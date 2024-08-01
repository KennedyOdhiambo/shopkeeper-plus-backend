package com.kennedy.shopkeeper_plus.dto.category;

import java.util.UUID;

public record CategoryListDto(
		UUID categoryId,

		String name,

		String description
) {
}
