package com.kennedy.shopkeeper_plus.dto.inventory;

import jakarta.validation.constraints.NotNull;

public record UpdateInventoryDto(

		@NotNull(message = "quantity must be specified")
		int quantityAdded
) {
}
