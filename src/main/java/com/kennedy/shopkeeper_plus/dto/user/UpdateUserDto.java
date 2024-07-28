package com.kennedy.shopkeeper_plus.dto.user;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateUserDto(
		@NotNull(message = "User Id is required")
		UUID id,

		String fullName,

		String phoneNumber,

		String businessName,

		UUID businessType,

		String businessLocation
) {
}
