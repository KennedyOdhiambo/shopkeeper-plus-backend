package com.kennedy.shopkeeper_plus.dto.salesItems;

import com.kennedy.shopkeeper_plus.models.Inventory;
import com.kennedy.shopkeeper_plus.models.Item;

import java.math.BigDecimal;

public record PartialSalesItem(
		Item item,

		Inventory inventory,

		Integer salesQuantity,

		BigDecimal unitPrice,

		BigDecimal totalPrice


) {

}
