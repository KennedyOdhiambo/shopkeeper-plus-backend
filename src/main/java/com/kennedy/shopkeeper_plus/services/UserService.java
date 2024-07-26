package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.user.NewUserDto;
import com.kennedy.shopkeeper_plus.models.BusinessType;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final BusinessTypeRepository businessTypeRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, BusinessTypeRepository businessTypeRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.businessTypeRepository = businessTypeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String createUser(NewUserDto newUserDto) {
		Optional<BusinessType> businessType = businessTypeRepository.findById(newUserDto.businessType());
		if (businessType.isEmpty()) {
			throw new ResourceNotFoundException("Business type not found");
		}

		Optional<User> existingUser = userRepository.findByPhoneNumber(newUserDto.phoneNumber());
		if (existingUser.isEmpty()) {
			throw new RuntimeException("Phone number already exists");
		}

		String hashedPassword = passwordEncoder.encode(newUserDto.password());

		User user = new User();
		user.setFullName(newUserDto.fullName());
		user.setPhoneNumber(newUserDto.phoneNumber());
		user.setPhoneNumber(newUserDto.phoneNumber());
		user.setPassword(hashedPassword);
		user.setBusinessName(newUserDto.businessName());
		user.setBusinessType(businessType.get());
		user.setBusinessLocation(newUserDto.businessLocation());

		userRepository.save(user);

		return "User created successfully";

	}
}