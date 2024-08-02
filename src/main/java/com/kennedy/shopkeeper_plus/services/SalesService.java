package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesItemDto;
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

@Service
public class SalesService {


	private final SalesRepository salesRepository;
	private final CustomerRepository customerRepository;
	private final InventoryRepository inventoryRepository;
	private final SalesItemsRepository salesItemsRepository;
	private final CreditDebtRepository creditDebtRepository;
	private final ItemsRepository itemsRepository;

	public SalesService(SalesRepository salesRepository, CustomerRepository customerRepository, InventoryRepository inventoryRepository, SalesItemsRepository salesItemsRepository, CreditDebtRepository creditDebtRepository, ItemsRepository itemsRepository) {
		this.salesRepository = salesRepository;
		this.customerRepository = customerRepository;
		this.inventoryRepository = inventoryRepository;
		this.salesItemsRepository = salesItemsRepository;
		this.creditDebtRepository = creditDebtRepository;
		this.itemsRepository = itemsRepository;
	}

	@Transactional
	public ResponseDto createSale(NewSalesDto newSalesDto) {
		try {
			var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LocalDate now = LocalDate.now();

			Customer customer = null;
			if (PaymentOptions.CREDIT.equals(newSalesDto.paymentOption())) {
				customer = customerRepository.findByIdAndStatus(newSalesDto.customerId(), EntityStatus.ACTIVE)
						           .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
			}

			BigDecimal totalCost = BigDecimal.ZERO;
			List<PartialSalesItem> partialSalesItemList = new ArrayList<>();

			for (NewSalesItemDto item : newSalesDto.items()) {
				var itemDetails = itemsRepository.findByIdAndStatus(item.itemId(), EntityStatus.ACTIVE)
						                  .orElseThrow(() -> new ResourceNotFoundException("Item with Id" + item.itemId() + "not found"));

				List<Inventory> availableInventory = inventoryRepository.findAvailableInventoryByItemId(item.itemId());
				if (availableInventory.isEmpty()) {
					throw new IllegalStateException("No inventory records for item" + item.itemId());
				}

				int totalQuantityInStock = availableInventory.stream()
						                           .mapToInt(Inventory::getQuantityInStock)
						                           .sum();

				if (totalQuantityInStock < item.quantity()) {
					throw new IllegalStateException("Insufficient inventory Level for item with id" + item.itemId());
				}

				int remainingQuantity = item.quantity();
				BigDecimal itemCost = BigDecimal.ZERO;

				for (Inventory invItem : availableInventory) {
					if (invItem.getQuantityInStock() > 0) {
						int deduction = Math.min(remainingQuantity, invItem.getQuantityInStock());
						invItem.setQuantityInStock(invItem.getQuantityInStock() - deduction);
						remainingQuantity -= deduction;
						BigDecimal deductionCost = invItem.getSellingPrice().multiply(BigDecimal.valueOf(deduction));
						itemCost = itemCost.add(deductionCost);

						inventoryRepository.save(invItem);

						partialSalesItemList.add(
								new PartialSalesItem(
										itemDetails,
										invItem,
										deduction,
										invItem.getSellingPrice(),
										deductionCost

								));
						if (remainingQuantity == 0) break;
					}
				}
				totalCost = totalCost.add(itemCost);
			}

			var sale = new Sales(
					userDetails,
					now,
					newSalesDto.paymentOption(),
					totalCost,
					customer,
					null
			);

			sale = salesRepository.save(sale);
			List<SalesItem> salesItems = new ArrayList<>();

			for (PartialSalesItem item : partialSalesItemList) {
				SalesItem salesItem = new SalesItem(
						sale,
						item.item(),
						item.inventory(),
						item.salesQuantity(),
						item.unitPrice(),
						item.totalPrice()
				);

				salesItems.add(salesItem);
			}

			salesItemsRepository.saveAll(salesItems);
			sale.setSalesItems(salesItems);
			salesRepository.save(sale);

			if (PaymentOptions.CREDIT.equals(newSalesDto.paymentOption())) {
				var creditDebt = new CreditDebt(
						userDetails,
						sale,
						now.atStartOfDay(),
						customer,
						totalCost,
						TransactionType.CREDIT,
						null
				);
				creditDebtRepository.save(creditDebt);
			}

			return new ResponseDto(
					ResponseStatus.success,
					"Sales successfully recorded",
					null
			);

		} catch (Exception e) {
			return new ResponseDto(
					ResponseStatus.fail,
					"Error recording sale:" + e,
					null
			);
		}
	}
}
