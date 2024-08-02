package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.common.PaginationResponseDto;
import com.kennedy.shopkeeper_plus.dto.common.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.customer.CreateCustomerDto;
import com.kennedy.shopkeeper_plus.dto.customer.CustomerResponseDto;
import com.kennedy.shopkeeper_plus.dto.customer.UpdateCustomerDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.Customer;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.CustomerRepository;
import com.kennedy.shopkeeper_plus.utils.AppConstants;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
	private static final Logger logger = LogManager.getLogger(CustomerService.class);

	private final CustomerRepository customerRepository;


	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public ResponseDto createCustomer(CreateCustomerDto createCustomerDto) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		var existingCustomerOptional = customerRepository.findByContactAndStatus(createCustomerDto.customerContact(), EntityStatus.ACTIVE);

		if (existingCustomerOptional.isPresent() && existingCustomerOptional.get().getUser().getId().equals(user.getId())) {
			throw new ResourceNotFoundException("Customer with similar contact already exists");
		}

		var customer = new Customer();
		customer.setUser(user);
		customer.setName(createCustomerDto.customerName());
		customer.setContact(createCustomerDto.customerContact());
		customer.setKraPin(createCustomerDto.kraPin());

		customer = customerRepository.save(customer);


		return new ResponseDto(
				ResponseStatus.success,
				"customer succesfully recorded",
				new CustomerResponseDto(
						customer.getId(),
						customer.getName(),
						customer.getContact(),
						customer.getKraPin()
				)
		);
	}

	public ResponseDto updateCustomer(UUID customerId, UpdateCustomerDto updateCustomerDto) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info(user.getId().toString());

		var customer = customerRepository.findByIdAndStatus(customerId, EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("customer not found"));

		logger.info(customer.getUser().getId().toString());

		if (!customer.getUser().getId().equals(user.getId())) {
			throw new ResourceNotFoundException("Customer not found");
		}


		customer.setName(updateCustomerDto.customerName());
		customer.setContact(updateCustomerDto.customerContact());
		customer.setKraPin(updateCustomerDto.kraPin());

		customer = customerRepository.save(customer);

		return new ResponseDto(
				ResponseStatus.success,
				"customer succesfully updated",
				new CustomerResponseDto(
						customer.getId(),
						customer.getName(),
						customer.getContact(),
						customer.getKraPin()
				)
		);
	}

	public ResponseDto listCustomers(int page) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int pageSize = AppConstants.PAGE_SIZE;
		page = Math.max(0, page - 1);

		Pageable pageable = PageRequest.of(page, pageSize);
		var customers = customerRepository.findActiveCustomersByUserId(user.getId(), pageable);

		var response = customers.getContent().stream()
				               .map(customer -> new CustomerResponseDto(
						               customer.getId(),
						               customer.getName(),
						               customer.getContact(),
						               customer.getKraPin()
				               )).toList();

		var paginatedResponse = new PaginationResponseDto<>(
				response,
				customers.getTotalElements(),
				customers.getTotalPages(),
				customers.getNumber() + 1
		);

		return new ResponseDto(
				ResponseStatus.success,
				"customers:",
				paginatedResponse
		);
	}

	public ResponseDto getCustomerById(UUID customerId) {
		var customer = customerRepository.findByIdAndStatus(customerId, EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("customer not found"));

		return new ResponseDto(
				ResponseStatus.success,
				"customer",
				new CustomerResponseDto(
						customer.getId(),
						customer.getName(),
						customer.getContact(),
						customer.getKraPin()
				)
		);
	}

	public ResponseDto deleteCustomer(UUID customerId) {
		var customer = customerRepository.findByIdAndStatus(customerId, EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("customer not found"));

		customer.setStatus(EntityStatus.DELETED);
		customerRepository.save(customer);

		return new ResponseDto(
				ResponseStatus.success,
				"customer succesfully deleted",
				null
		);
	}
}
