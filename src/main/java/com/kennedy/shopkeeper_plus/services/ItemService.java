package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.item.ItemResponseDto;
import com.kennedy.shopkeeper_plus.dto.item.NewItemDto;
import com.kennedy.shopkeeper_plus.dto.item.UpdateItemDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Item;
import com.kennedy.shopkeeper_plus.repositories.CategoryRepository;
import com.kennedy.shopkeeper_plus.repositories.ItemsRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
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
		var category = categoryRepository.findByIdAndStatus(newItemDto.categoryId(), EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		var item = new Item();
		item.setCategory(category);
		item.setName(newItemDto.name());
		item.setReorderLevel(newItemDto.reorderLevel());
		item.setUnitOfMeasure(newItemDto.unitOfMeasure());

		itemsRepository.save(item);

		return new ResponseDto(
				ResponseStatus.success,
				"Item successfully added to your shop",
				new ItemResponseDto(
						item.getId(),
						item.getCategory(),
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
						item.getCategory(),
						item.getName(),
						item.getUnitOfMeasure(),
						item.getReorderLevel()
				)
		);
	}

}
