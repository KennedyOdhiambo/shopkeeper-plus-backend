package com.kennedy.shopkeeper_plus.dto.errors;

import java.util.List;

public record ErrorResponseDTO(
		String status,

		String message,

		List<FieldErrorDTO> errors
) {
}
