package com.kennedy.shopkeeper_plus.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewItemDto(
		@NotNull(message = "categoryId is required")
		UUID categoryId,

		@NotBlank(message = "Item name is required")
		String name,

		@NotBlank(message = "Unit of measure is required")
		String unitOfMeasure,
		
		Integer reorderLevel
) {
}
