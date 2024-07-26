package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.BusinessTypeResponseDto;
import com.kennedy.shopkeeper_plus.dto.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.dto.UpdateBusinessTypeDto;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.services.BusinessTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business-types")
public class BusinessTypeController {

	private final BusinessTypeService businessTypeService;


	public BusinessTypeController(BusinessTypeService businessTypeService) {
		this.businessTypeService = businessTypeService;
	}

	@PostMapping("/create")
	public BusinessTypeResponseDto createBusinessType(
			@Valid
			@RequestBody
			NewBusinessTypeDto newBusinessTypeDto) {
		return businessTypeService.createBusinessType(newBusinessTypeDto);
	}

	@GetMapping("/list")
	public List<BusinessType> getActiveBusinessTypes() {
		return businessTypeService.getActiveBusinessTypes();
	}

	@PatchMapping("/update")
	public BusinessTypeResponseDto updateBusinessType(
			@Valid
			@RequestBody
			UpdateBusinessTypeDto updateBusinessTypeDto) {
		return businessTypeService.updateBusinessType(updateBusinessTypeDto);
	}
}
