package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.business_types.BusinessTypeResponseDto;
import com.kennedy.shopkeeper_plus.dto.business_types.BusinessTypeResponseMessage;
import com.kennedy.shopkeeper_plus.dto.business_types.NewBusinessTypeDto;
import com.kennedy.shopkeeper_plus.dto.business_types.UpdateBusinessTypeDto;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.services.BusinessTypeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business-types")
public class BusinessTypeController {

	private final BusinessTypeService businessTypeService;

	public BusinessTypeController(BusinessTypeService businessTypeService) {
		this.businessTypeService = businessTypeService;
	}

	@PostMapping("/create")
	public BusinessTypeResponseDto createBusinessType(
			@Valid @RequestBody NewBusinessTypeDto newBusinessTypeDto) {
		return businessTypeService.createBusinessType(newBusinessTypeDto);
	}

	@GetMapping("/list")
	public List<BusinessType> getActiveBusinessTypes() {
		return businessTypeService.getActiveBusinessTypes();
	}

	@PatchMapping("/update")
	public BusinessTypeResponseDto updateBusinessType(
			@Valid @RequestBody UpdateBusinessTypeDto updateBusinessTypeDto) {
		return businessTypeService.updateBusinessType(updateBusinessTypeDto);
	}

	@DeleteMapping("/delete/{id}")
	public BusinessTypeResponseMessage deleteBusinessType(
			@PathVariable UUID id) {
		return businessTypeService.deleteBusinessType(id);
	}
}
