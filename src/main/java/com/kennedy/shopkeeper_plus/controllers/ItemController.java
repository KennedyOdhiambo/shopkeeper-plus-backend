package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.item.NewItemDto;
import com.kennedy.shopkeeper_plus.dto.item.UpdateItemDto;
import com.kennedy.shopkeeper_plus.services.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/items")

public class ItemController {
	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping("/create")
	public ResponseDto createItem(
			@Valid @RequestBody NewItemDto newItemDto
	) {
		return itemService.createItem(newItemDto);
	}

	@PatchMapping("/update/{itemId}")
	public ResponseDto updateItem(
			@PathVariable UUID itemId,
			@Valid @RequestBody UpdateItemDto updateItemDto
	) {
		return itemService.updateItem(itemId, updateItemDto);
	}

	@GetMapping("/list/{categoryId}")
	public ResponseDto listItemsByCategory(
			@PathVariable UUID categoryId
	) {
		return itemService.listItemsByCategory(categoryId);
	}

	@DeleteMapping("/delete/{itemId}")
	public ResponseDto deleteItem(
			@PathVariable UUID itemId
	) {
		return itemService.deleteItem(itemId);
	}

	@GetMapping("/{itemId}")
	public ResponseDto getItemById(
			@PathVariable UUID itemId
	) {
		return itemService.getItemById(itemId);
	}

}
