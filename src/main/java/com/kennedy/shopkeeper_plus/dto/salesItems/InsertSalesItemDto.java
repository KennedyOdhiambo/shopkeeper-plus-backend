package com.kennedy.shopkeeper_plus.dto.salesItems;

import com.kennedy.shopkeeper_plus.models.Inventory;
import com.kennedy.shopkeeper_plus.models.Item;
import com.kennedy.shopkeeper_plus.models.Sales;

import java.math.BigDecimal;

public record InsertSalesItemDto(
		Sales sale,

		Item item,

		Inventory inventory,

		Integer salesQuantity,

		BigDecimal unitPrice,

		BigDecimal totalPrice
) {
}
