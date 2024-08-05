package com.kennedy.shopkeeper_plus.dto.sales;

import com.kennedy.shopkeeper_plus.dto.salesItems.NewSalesItemResponseDto;
import com.kennedy.shopkeeper_plus.enums.PaymentOptions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SalesResponseDto(
		UUID salesId,

		LocalDate salesDate,

		PaymentOptions paymentOption,

		BigDecimal totalCost,

		List<NewSalesItemResponseDto> salesItems

) {
}
