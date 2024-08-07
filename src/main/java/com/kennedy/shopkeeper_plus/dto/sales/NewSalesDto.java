package com.kennedy.shopkeeper_plus.dto.sales;

import com.kennedy.shopkeeper_plus.enums.PaymentOptions;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NewSalesDto(
		LocalDate salesDate,

		PaymentOptions paymentOption,

		UUID customerId,


		List<NewSalesItemDto> items
) {


}
