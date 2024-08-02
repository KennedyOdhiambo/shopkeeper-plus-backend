package com.kennedy.shopkeeper_plus.dto.item;

import java.util.UUID;

public record ItemResponseDto(
		UUID itemId,

		String itemName,

		String unitOfMeasure,

		Integer reorderLevel
) {
}
