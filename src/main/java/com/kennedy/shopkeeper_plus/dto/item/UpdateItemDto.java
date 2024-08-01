package com.kennedy.shopkeeper_plus.dto.item;

import jakarta.validation.constraints.NotBlank;

public record UpdateItemDto(


		@NotBlank(message = "Item name is required")
		String name,

		@NotBlank(message = "Unit of measure is required")
		String unitOfMeasure,

		Integer reorderLevel
) {
}
