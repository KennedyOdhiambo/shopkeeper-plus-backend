package com.kennedy.shopkeeper_plus.dto.business_types;


import com.kennedy.shopkeeper_plus.models.BusinessType;

public record BusinessTypeResponseDto(
		String message,

		BusinessType businessType

) {
}
