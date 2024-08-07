package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesDto;
import com.kennedy.shopkeeper_plus.enums.PaymentOptions;
import com.kennedy.shopkeeper_plus.services.SalesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/sales")
public class SalesController {
	private final SalesService salesService;


	public SalesController(SalesService salesService) {
		this.salesService = salesService;
	}

	@PostMapping("/create")
	public ResponseDto createSale(
			@Valid @RequestBody NewSalesDto newSalesDto
	) {
		return salesService.createSale(newSalesDto);
	}

	@GetMapping("/list")
	public ResponseDto listSales(@RequestParam(value = "page", defaultValue = "1") int page,
	                             @RequestParam(value = "startDate", required = false) LocalDate startDate,
	                             @RequestParam(value = "endDate", required = false) LocalDate endDate,
	                             @RequestParam(value = "paymentOptions", required = false) PaymentOptions paymentOptions,
	                             @RequestParam(value = "customerId", required = false) UUID customerId) {
		return salesService.listSales(page, startDate, endDate, paymentOptions, customerId);
	}
}
