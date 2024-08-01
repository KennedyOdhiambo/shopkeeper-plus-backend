package com.kennedy.shopkeeper_plus.dto.authentication;

import com.kennedy.shopkeeper_plus.models.BusinessType;

public record LoginResponseDto(
		String fullName,

		String phoneNumber,

		String businessName,

		BusinessType businessType
) {
}
