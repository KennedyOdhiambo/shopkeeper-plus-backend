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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
	private final CustomerRepository customerRepository;


	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public ResponseDto createCustomer(CreateCustomerDto createCustomerDto) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

		var customer = customerRepository.findByIdAndStatus(customerId, EntityStatus.ACTIVE)
				               .orElseThrow(() -> new ResourceNotFoundException("customer not found"));

		if (customer.getUser().getId() != user.getId()) {
			throw new ResourceNotFoundException("customer not found");
		}

		customer.setName(updateCustomerDto.customerName());
		customer.setContact(updateCustomerDto.customerContact());
		customer.setKraPin(updateCustomerDto.kraPin());

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
