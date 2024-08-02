package com.kennedy.shopkeeper_plus.dto.common;

import com.kennedy.shopkeeper_plus.enums.ResponseStatus;

public record ResponseDto(
		ResponseStatus status,

		String message,

		Object data
) {
}
