package com.kennedy.shopkeeper_plus.dto.sales;

import com.kennedy.shopkeeper_plus.enums.PaymentOptions;

import java.util.UUID;

public record ListSalesRequestDto(
		String startDate,

		String endDate,

		PaymentOptions paymentOptions,

		UUID customerId,

		int page

) {
}
