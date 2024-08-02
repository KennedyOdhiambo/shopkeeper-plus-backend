package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.inventory.NewInventoryDto;
import com.kennedy.shopkeeper_plus.dto.inventory.UpdateInventoryDto;
import com.kennedy.shopkeeper_plus.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@PostMapping("/add")
	public ResponseDto newInventory(
			@Valid @RequestBody NewInventoryDto newInventoryDto
	) {
		return inventoryService.newInventory(newInventoryDto);
	}

	@PatchMapping("/update/{inventoryId}")
	public ResponseDto updateInventory(
			@PathVariable UUID inventoryId,
			@Valid @RequestBody UpdateInventoryDto updateInventoryDto
	) {
		return inventoryService.updateInventory(inventoryId, updateInventoryDto);
	}

	@GetMapping("/item/{itemId}")
	public ResponseDto listItemInventory(
			@PathVariable UUID itemId,
			@RequestParam(defaultValue = "0") int page
	) {
		return inventoryService.listItemInventory(itemId, page);
	}


	@DeleteMapping("/delete/{inventoryId}")
	public ResponseDto deleteInventory(
			@PathVariable UUID inventoryId
	) {
		return inventoryService.deleteInventory(inventoryId);
	}

}
