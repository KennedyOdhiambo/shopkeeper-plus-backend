package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.business_types.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.dto.business_types.UpdateBusinessTypeDto;
import com.kennedy.shopkeeper_plus.services.BusinessTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/business-types")
public class BusinessTypeController {

	private final BusinessTypeService businessTypeService;

	public BusinessTypeController(BusinessTypeService businessTypeService) {
		this.businessTypeService = businessTypeService;
	}

	@PostMapping("/create")
	public ResponseDto createBusinessType(
			@Valid @RequestBody NewBusinessTypeDto newBusinessTypeDto) {
		return businessTypeService.createBusinessType(newBusinessTypeDto);
	}

	@GetMapping("/list")
	public ResponseDto getActiveBusinessTypes() {
		return businessTypeService.getActiveBusinessTypes();
	}

	@PatchMapping("/update")
	public ResponseDto updateBusinessType(
			@Valid @RequestBody UpdateBusinessTypeDto updateBusinessTypeDto) {
		return businessTypeService.updateBusinessType(updateBusinessTypeDto);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseDto deleteBusinessType(
			@PathVariable UUID id) {
		return businessTypeService.deleteBusinessType(id);
	}
}
