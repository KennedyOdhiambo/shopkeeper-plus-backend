package com.kennedy.shopkeeper_plus.dto;

import com.kennedy.shopkeeper_plus.enums.ResponseStatus;

public record ResponseDto(
		ResponseStatus status,

		String message,

		Object data
) {
}
