package com.kennedy.shopkeeper_plus.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateCategoryDto(
		@NotNull(message = "categoryId is required")
		UUID categoryId,

		@NotBlank(message = "category name is required")
		String name,

		@NotBlank(message = "category description is required")
		String description) {


}
