package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesItemDto;
import com.kennedy.shopkeeper_plus.dto.sales.SalesResponseDto;
import com.kennedy.shopkeeper_plus.dto.salesItems.NewSalesItemResponseDto;
import com.kennedy.shopkeeper_plus.dto.salesItems.PartialSalesItem;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.PaymentOptions;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.enums.TransactionType;
import com.kennedy.shopkeeper_plus.models.*;
import com.kennedy.shopkeeper_plus.repositories.*;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesService {


	private final SalesRepository salesRepository;
	private final CustomerRepository customerRepository;
	private final InventoryRepository inventoryRepository;
	private final SalesItemsRepository salesItemsRepository;
	private final CreditDebtRepository creditDebtRepository;
	private final ItemsRepository itemsRepository;

	public SalesService(SalesRepository salesRepository, CustomerRepository customerRepository,
	                    InventoryRepository inventoryRepository, SalesItemsRepository salesItemsRepository,
	                    CreditDebtRepository creditDebtRepository, ItemsRepository itemsRepository) {
		this.salesRepository = salesRepository;
		this.customerRepository = customerRepository;
		this.inventoryRepository = inventoryRepository;
		this.salesItemsRepository = salesItemsRepository;
		this.creditDebtRepository = creditDebtRepository;
		this.itemsRepository = itemsRepository;
	}

	@Transactional
	public ResponseDto createSale(NewSalesDto newSalesDto) {

		var userDetails = getCurrentUser();
		LocalDate now = LocalDate.now();

		var customer = getCustomerForSale(newSalesDto);
		List<PartialSalesItem> partialSalesItemList = processItems(newSalesDto.items());
		BigDecimal totalCost = calculateTotalCost(partialSalesItemList);

		Sales sale = createAndSaveSale(userDetails, now, newSalesDto.paymentOption(), totalCost, customer);
		List<SalesItem> salesItems = createAndSaveSalesItems(sale, partialSalesItemList);

		handleCreditSale(newSalesDto, sale, customer, totalCost, now);


		return new ResponseDto(
				ResponseStatus.success,
				"Sales successfully recorded",
				new SalesResponseDto(
						sale.getId(),
						sale.getSalesDate(),
						sale.getPaymentOption(),
						sale.getTotalCost(),
						toSalesItemResponseDtos(salesItems)
				)
		);

	}

	private List<NewSalesItemResponseDto> toSalesItemResponseDtos(List<SalesItem> salesItems) {
		return salesItems.stream()
				       .map(item -> new NewSalesItemResponseDto(
						       item.getId(),
						       item.getItem().getName(),
						       item.getSalesQuantity(),
						       item.getUnitPrice(),
						       item.getTotalPrice()
				       )).toList();
	}

	private User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private Customer getCustomerForSale(NewSalesDto newSalesDto) {
		Optional<Customer> customer = customerRepository.findByIdAndStatus(newSalesDto.customerId(), EntityStatus.ACTIVE);
		if (PaymentOptions.CREDIT.equals(newSalesDto.paymentOption()) && customer.isEmpty()) {
			throw new IllegalStateException("Customer is required for credit sale");
		}

		return customer.orElse(null);
	}

	private List<PartialSalesItem> processItems(List<NewSalesItemDto> items) {
		List<PartialSalesItem> partialSalesItemList = new ArrayList<>();

		for (NewSalesItemDto item : items) {
			var itemDetails = itemsRepository.findByIdAndStatus(item.itemId(), EntityStatus.ACTIVE)
					                  .orElseThrow(() -> new IllegalStateException("Item with Id" + item.itemId() + "not found"));

			List<Inventory> availableInventory = inventoryRepository.findAvailableInventoryByItemId(item.itemId());
			if (availableInventory.isEmpty()) {
				throw new ResourceNotFoundException("No inventory items for item" + itemDetails.getName());
			}
			validateInventoryLevel(availableInventory, item);
			partialSalesItemList.addAll(deductInventory(itemDetails, availableInventory, item.quantity()));

		}
		return partialSalesItemList;

	}

	private void validateInventoryLevel(List<Inventory> availableInventory, NewSalesItemDto item) {
		int totalQuantityInStock = availableInventory.stream()
				                           .mapToInt(Inventory::getQuantityInStock)
				                           .sum();

		if (totalQuantityInStock < item.quantity()) {
			throw new IllegalStateException("Insufficient inventory level for item with id " + item.itemId());
		}
	}

	private List<PartialSalesItem> deductInventory(Item itemDetails, List<Inventory> availableInventory, int requestedQuantity) {
		List<PartialSalesItem> partialSalesItems = new ArrayList<>();
		int remainingQuantity = requestedQuantity;

		for (Inventory invItem : availableInventory) {
			if (invItem.getQuantityInStock() > 0) {
				int deduction = Math.min(remainingQuantity, invItem.getQuantityInStock());
				invItem.setQuantityInStock(invItem.getQuantityInStock() - deduction);
				remainingQuantity -= deduction;
				BigDecimal deductionCost = invItem.getSellingPrice().multiply(BigDecimal.valueOf(deduction));

				inventoryRepository.save(invItem);

				partialSalesItems.add(new PartialSalesItem(itemDetails, invItem, deduction, invItem.getSellingPrice(), deductionCost));

				if (remainingQuantity == 0) break;
			}
		}
		return partialSalesItems;
	}

	private BigDecimal calculateTotalCost(List<PartialSalesItem> partialSalesItemList) {
		return partialSalesItemList.stream()
				       .map(PartialSalesItem::totalPrice)
				       .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Sales createAndSaveSale(User userDetails, LocalDate now, PaymentOptions paymentOption, BigDecimal totalCost, Customer customer) {
		Sales sale = new Sales(userDetails, now, paymentOption, totalCost, customer, null);
		return salesRepository.save(sale);
	}

	private List<SalesItem> createAndSaveSalesItems(Sales sale, List<PartialSalesItem> partialSalesItemList) {
		List<SalesItem> salesItems = partialSalesItemList.stream()
				                             .map(item -> new SalesItem(sale, item.item(), item.inventory(), item.salesQuantity(), item.unitPrice(), item.totalPrice()))
				                             .collect(Collectors.toList());

		salesItemsRepository.saveAll(salesItems);
		sale.setSalesItems(salesItems);
		salesRepository.save(sale);
		return salesItems;
	}

	private void handleCreditSale(NewSalesDto newSalesDto, Sales sale, Customer customer, BigDecimal totalCost, LocalDate now) {
		if (PaymentOptions.CREDIT.equals(newSalesDto.paymentOption())) {
			CreditDebt creditDebt = new CreditDebt(getCurrentUser(), sale, now.atStartOfDay(), customer, totalCost, TransactionType.CREDIT, null);
			creditDebtRepository.save(creditDebt);
		}
	}

}

