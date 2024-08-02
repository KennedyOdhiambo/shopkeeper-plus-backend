package com.kennedy.shopkeeper_plus.dto.inventory;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record NewInventoryDto(
		@NotNull(message = "itemId is required")
		UUID itemId,

		@NotNull(message = "quantity added must be specified")
		int quantityAdded,

		@NotNull(message = "buying price is required")
		BigDecimal buyingPrice,

		@NotNull(message = "selling price is required")
		BigDecimal sellingPrice
) {
}
