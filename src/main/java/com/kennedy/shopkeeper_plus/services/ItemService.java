package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.item.ItemResponseDto;
import com.kennedy.shopkeeper_plus.dto.item.NewItemDto;
import com.kennedy.shopkeeper_plus.dto.item.UpdateItemDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Item;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.CategoryRepository;
import com.kennedy.shopkeeper_plus.repositories.ItemsRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemService {
	private final ItemsRepository itemsRepository;
	private final CategoryRepository categoryRepository;

	public ItemService(ItemsRepository itemsRepository, CategoryRepository categoryRepository) {
		this.itemsRepository = itemsRepository;
		this.categoryRepository = categoryRepository;
	}

	public ResponseDto createItem(NewItemDto newItemDto) {
		var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		boolean itemExists = itemsRepository.existsByNameIgnoreCaseAndUserAndStatus(
				newItemDto.name(), user, EntityStatus.ACTIVE
		);

		if (itemExists) {
			return new ResponseDto(
					ResponseStatus.fail,
					"An item with a similar name already exists in your shop",
					null
			);
		}

		var category = categoryRepository.findByIdAndStatus(newItemDto.categoryId(), EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		var item = new Item();
		item.setUser(user);
		item.setCategory(category);
		item.setName(newItemDto.name());
		item.setReorderLevel(newItemDto.reorderLevel());
		item.setUnitOfMeasure(newItemDto.unitOfMeasure());
		item.setStatus(EntityStatus.ACTIVE);  // Assuming you want to set the status

		itemsRepository.save(item);

		return new ResponseDto(
				ResponseStatus.success,
				"Item successfully added to your shop",
				new ItemResponseDto(
						item.getId(),
						item.getName(),
						item.getUnitOfMeasure(),
						item.getReorderLevel()
				)
		);
	}

	public ResponseDto updateItem(UUID itemId, UpdateItemDto updateItemDto) {
		var item = itemsRepository.findByIdAndStatus(itemId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

		item.setName(updateItemDto.name());
		item.setUnitOfMeasure(updateItemDto.unitOfMeasure());
		item.setReorderLevel(updateItemDto.reorderLevel());

		itemsRepository.save(item);


		return new ResponseDto(
				ResponseStatus.success,
				"Item successfully updated",
				new ItemResponseDto(
						item.getId(),
						item.getName(),
						item.getUnitOfMeasure(),
						item.getReorderLevel()));
	}

	public ResponseDto listItemsByCategory(UUID categoryId) {
		var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		var category = categoryRepository.findByIdAndStatus(categoryId, EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		var items = itemsRepository.findByCategoryIdAndUserIdAndStatus(category.getId(), user.getId(), EntityStatus.ACTIVE);

		var response = items.stream()
				               .map(item -> new ItemResponseDto(
						               item.getId(),
						               item.getName(),
						               item.getUnitOfMeasure(),
						               item.getReorderLevel()
				               )).toList();

		return new ResponseDto(
				ResponseStatus.success,
				"Items",
				response
		);
	}

	public ResponseDto deleteItem(UUID itemId) {
		var item = itemsRepository.findByIdAndStatus(itemId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("ITem not found"));

		item.setStatus(EntityStatus.DELETED);
		itemsRepository.save(item);

		return new ResponseDto(
				ResponseStatus.success,
				"Item successfully deleted",
				null
		);
	}

	public ResponseDto getItemById(UUID itemId) {
		var item = itemsRepository.findByIdAndStatus(itemId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

		var response = new ItemResponseDto(
				item.getId(),
				item.getName(),
				item.getUnitOfMeasure(),
				item.getReorderLevel()
		);
		return new ResponseDto(
				ResponseStatus.success,
				"Item",
				response
		);
	}


}
