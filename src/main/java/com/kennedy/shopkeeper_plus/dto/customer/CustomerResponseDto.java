package com.kennedy.shopkeeper_plus.dto.customer;

import java.util.UUID;

public record CustomerResponseDto(
		UUID customerId,

		String customerName,

		String customerContact,

		String KraPin
) {

}
