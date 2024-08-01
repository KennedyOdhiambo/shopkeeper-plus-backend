package com.kennedy.shopkeeper_plus.dto.sales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NewSalesItemDto(
		@NotNull(message = "ItemId is required")
		UUID itemId,

		@NotBlank(message = "quantity is required")
		int quantity
) {
}
