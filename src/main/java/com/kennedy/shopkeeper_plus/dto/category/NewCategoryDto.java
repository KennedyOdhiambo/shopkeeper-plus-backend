package com.kennedy.shopkeeper_plus.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewCategoryDto(
		@NotBlank(message = "category name is required")
		@Size(min = 2, max = 256, message = "name should be between 2-256 characters")
		String name,

		@NotBlank(message = "category description is required")
		String description
) {
}
