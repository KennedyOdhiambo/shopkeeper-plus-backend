package com.kennedy.shopkeeper_plus.dto.inventory;

import com.kennedy.shopkeeper_plus.dto.item.ItemResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InventoryResponseDto(
		UUID inventoryId,

		ItemResponseDto item,

		int quantityAdded,

		int quantityInStock,

		BigDecimal buyingPrice,

		BigDecimal sellingPrice,

		LocalDateTime lastUpdated

) {
}
