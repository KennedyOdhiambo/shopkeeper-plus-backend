package com.kennedy.shopkeeper_plus.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateInventoryDto(
		@NotNull(message = " inventoryId is required")
		UUID inventoryId,

		@NotBlank(message = "quantity must be specified")
		int quantityAdded
) {
}
