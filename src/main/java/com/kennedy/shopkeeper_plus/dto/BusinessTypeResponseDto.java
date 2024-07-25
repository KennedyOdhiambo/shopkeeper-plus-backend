package com.kennedy.shopkeeper_plus.dto;


import com.kennedy.shopkeeper_plus.models.BusinessType;

public record BusinessTypeResponseDto(
		String message,

		BusinessType businessType

) {
}
