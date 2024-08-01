package com.kennedy.shopkeeper_plus.dto.item;

import com.kennedy.shopkeeper_plus.models.Category;

import java.util.UUID;

public record ItemResponseDto(
		UUID itemId,

		Category category,

		String itemName,

		String unitOfMeasure,

		Integer reorderLevel
) {
}
