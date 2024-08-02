package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.customer.CreateCustomerDto;
import com.kennedy.shopkeeper_plus.dto.customer.UpdateCustomerDto;
import com.kennedy.shopkeeper_plus.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/create")
	public ResponseDto createCustomer(
			@Valid @RequestBody CreateCustomerDto createCustomerDto) {
		return customerService.createCustomer(createCustomerDto);
	}

	@PatchMapping("/update/{customerId}")
	public ResponseDto updateCustomer(
			@PathVariable UUID customerId,
			@Valid @RequestBody UpdateCustomerDto updateCustomerDto) {
		return customerService.updateCustomer(customerId, updateCustomerDto);
	}

	@GetMapping("/list")
	public ResponseDto listCustomers(
			@RequestParam(defaultValue = "0") int page
	) {
		return customerService.listCustomers(page);
	}

	@GetMapping("/{customerId}")
	public ResponseDto getCustomerById(
			@PathVariable UUID customerId) {
		return customerService.getCustomerById(customerId);
	}

	@DeleteMapping("/delete/{customerId}")
	public ResponseDto deleteCustomer(
			@PathVariable UUID customerId
	) {
		return customerService.deleteCustomer(customerId);
	}
}
