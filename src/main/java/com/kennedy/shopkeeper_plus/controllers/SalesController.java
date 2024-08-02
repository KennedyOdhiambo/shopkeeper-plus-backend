package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.sales.NewSalesDto;
import com.kennedy.shopkeeper_plus.services.SalesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
