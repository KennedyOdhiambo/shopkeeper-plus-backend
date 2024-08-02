package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.inventory.InventoryResponseDto;
import com.kennedy.shopkeeper_plus.dto.inventory.NewInventoryDto;
import com.kennedy.shopkeeper_plus.dto.inventory.UpdateInventoryDto;
import com.kennedy.shopkeeper_plus.dto.item.ItemResponseDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Inventory;
import com.kennedy.shopkeeper_plus.repositories.InventoryRepository;
import com.kennedy.shopkeeper_plus.repositories.ItemsRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InventoryService {
	private final InventoryRepository inventoryRepository;
	private final ItemsRepository itemsRepository;

	public InventoryService(InventoryRepository inventoryRepository, ItemsRepository itemsRepository) {
		this.inventoryRepository = inventoryRepository;
		this.itemsRepository = itemsRepository;
	}

	public ResponseDto addInventory(NewInventoryDto newInventoryDto) {
		var item = itemsRepository.findByIdAndStatus(newInventoryDto.itemId(), EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

		var now = LocalDateTime.now();
		var inventory = new Inventory();
		inventory.setItem(item);
		inventory.setBuyingPrice(newInventoryDto.buyingPrice());
		inventory.setSellingPrice(newInventoryDto.sellingPrice());
		inventory.setLastUpdated(now);
		inventory.setQuantityInStock(newInventoryDto.quantityAdded());
		inventory.setQuantityAdded(newInventoryDto.quantityAdded());

		inventory = inventoryRepository.save(inventory);

		var response = new InventoryResponseDto(
				inventory.getId(),
				new ItemResponseDto(
						item.getId(),
						item.getName(),
						item.getUnitOfMeasure(),
						item.getReorderLevel()
				),
				inventory.getQuantityAdded(),
				inventory.getQuantityInStock(),
				inventory.getBuyingPrice(),
				inventory.getSellingPrice(),
				inventory.getLastUpdated()
		);

		return new ResponseDto(
				ResponseStatus.success,
				"Inventory successfully recorded",
				response
		);
	}

	public ResponseDto updateInventory(UpdateInventoryDto updateInventoryDto) {
		var inventoryEntry = inventoryRepository.findByIdAndStatus(updateInventoryDto.inventoryId(), EntityStatus.ACTIVE)
				                     .orElseThrow(() -> new ResourceNotFoundException("Inventory entry does not exist"));

		inventoryEntry.setQuantityInStock(inventoryEntry.getQuantityInStock() + updateInventoryDto.quantityAdded());
		inventoryEntry.setQuantityAdded(inventoryEntry.getQuantityAdded() + updateInventoryDto.quantityAdded());
		inventoryEntry.setLastUpdated(LocalDateTime.now());

		var updatedInventory = inventoryRepository.save(inventoryEntry);
		var response = new InventoryResponseDto(
				inventoryEntry.getId(),
				new ItemResponseDto(
						inventoryEntry.getItem().getId(),
						inventoryEntry.getItem().getName(),
						inventoryEntry.getItem().getUnitOfMeasure(),
						inventoryEntry.getItem().getReorderLevel()
				),
				inventoryEntry.getQuantityAdded(),
				inventoryEntry.getQuantityInStock(),
				inventoryEntry.getBuyingPrice(),
				inventoryEntry.getSellingPrice(),
				inventoryEntry.getLastUpdated()
		);
		return new ResponseDto(
				ResponseStatus.success,
				"Inventory successfully update",
				response
		);
	}

	public ResponseDto listItemInventory(UUID itemId) {
		var item = itemsRepository.findByIdAndStatus(itemId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

		var inventoryEntries = inventoryRepository.findAvailableInventoryByItemId(itemId);
		var response = inventoryEntries.stream()
				               .map(entry -> new InventoryResponseDto(
						               entry.getId(),
						               new ItemResponseDto(
								               item.getId(),
								               item.getName(),
								               item.getUnitOfMeasure(),
								               item.getReorderLevel()
						               ),
						               entry.getQuantityAdded(),
						               entry.getQuantityInStock(),
						               entry.getBuyingPrice(),
						               entry.getSellingPrice(),
						               entry.getLastUpdated()
				               )).toList();

		return new ResponseDto(
				ResponseStatus.success,
				"Inventory entries for" + item.getName(),
				response
		);
	}

	public ResponseDto deleteInventory(UUID inventoryId) {
		var entry = inventoryRepository.findByIdAndStatus(inventoryId, EntityStatus.ACTIVE)
				            .orElseThrow(() -> new ResourceNotFoundException("Inventory entry not found"));

		entry.setStatus(EntityStatus.DELETED);
		inventoryRepository.save(entry);

		return new ResponseDto(
				ResponseStatus.success,
				"IInventory entry succesfully deleted",
				null
		);
	}
}
