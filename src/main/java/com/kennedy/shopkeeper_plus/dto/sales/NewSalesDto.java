package com.kennedy.shopkeeper_plus.dto.sales;

import com.kennedy.shopkeeper_plus.enums.PaymentOptions;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NewSalesDto(
		LocalDate salesDate,
		
		PaymentOptions paymentOption,

		UUID customerId,

		@NotBlank(message = "items are required")
		List<NewSalesItemDto> items
) {


}
