package com.kennedy.shopkeeper_plus.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewUserDto(

		@NotBlank(message = "Full name is required")
		@Size(max = 256, message = "Full name must be less than 256 characters")
		String fullName,

		@NotBlank(message = "Phone number is required")
		@Size(min = 10, max = 13, message = "Phone number must be between 10-13 characters")
		String phoneNumber,

		@NotBlank(message = "Password is required")
		@Size(min = 6, max = 256, message = "Password must be between 8-256 characters")
		String password,

		@NotBlank(message = "Business name is required")
		@Size(max = 256, message = "Business name must be les than 256 characters")
		String businessName,

		@NotNull(message = "Business type is required")
		UUID businessType,

		@NotBlank(message = "Business location is required")
		@Size(max = 256, message = "Business location must be less than 256 characters")
		String businessLocation
) {
}
