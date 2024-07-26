package com.kennedy.shopkeeper_plus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateBusinessTypeDto(
		@NotNull(message = "Business type id is required")
		UUID id,

		@NotBlank(message = "Business type name is required")
		@Size(min = 2, max = 256, message = "Business type name should be between 2 - 256 characters")
		String name
) {
}
