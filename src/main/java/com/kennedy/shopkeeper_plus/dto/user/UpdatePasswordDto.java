package com.kennedy.shopkeeper_plus.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdatePasswordDto(
		@NotNull(message = "User Id is required")
		UUID userId,

		@NotBlank(message = "old password is required")
		@Size(min = 6, message = "Password must be at least 6 characters")
		String oldPassword,

		@NotBlank(message = "old password is required")
		@Size(min = 6, message = "Password must be at least 6 characters")
		String newPassword
) {
}
