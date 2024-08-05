package com.kennedy.shopkeeper_plus.dto.salesItems;

import java.math.BigDecimal;
import java.util.UUID;

public record NewSalesItemResponseDto(
		UUID salesItemId,

		String itemName,

		Integer salesQuantity,

		BigDecimal unitPrice,

		BigDecimal totalPrice
) {
}
