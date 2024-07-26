package com.kennedy.shopkeeper_plus.dto.business_types;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewBusinessTypeDto(
		@NotBlank(message = "Business type name is required")
		@Size(min = 2, max = 256, message = "Business type name should be between 2 - 256 characters")
		String name
) {
}
