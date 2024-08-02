package com.kennedy.shopkeeper_plus.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record UpdateCustomerDto(


		@NotBlank(message = "customer name is required")
		String customerName,

		@NotBlank(message = "customer contact is required")
		String customerContact,

		String kraPin
) {
}
