package com.kennedy.shopkeeper_plus.dto.user;

import com.kennedy.shopkeeper_plus.models.BusinessType;

import java.time.LocalDateTime;

public record UserResponseDto(
		String fullName,

		String phoneNumber,

		String businessName,

		String businessLocation,

		LocalDateTime dateJoined,

		BusinessType businessType

) {
}
