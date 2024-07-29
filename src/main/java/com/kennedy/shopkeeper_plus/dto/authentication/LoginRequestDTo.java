package com.kennedy.shopkeeper_plus.dto.authentication;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LoginRequestDTo(
		@NotNull(message = "User Id is required")
		UUID userId,

		String phoneNumber,
		
		String password
) {
}
