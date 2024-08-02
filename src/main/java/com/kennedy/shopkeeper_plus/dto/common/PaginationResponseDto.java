package com.kennedy.shopkeeper_plus.dto.common;

import java.util.List;

public record PaginationResponseDto<T>(
		List<T> content,

		long totalElement,

		int totalPages,

		int currentPage
) {
}
