package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.user.NewUserDto;
import com.kennedy.shopkeeper_plus.dto.user.UserResponseDto;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceAlreadyExistsException;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

	public ResponseDto createUser(NewUserDto newUserDto) {
		var businessTypeOptional = businessTypeRepository.findById(newUserDto.businessType());
		if (businessTypeOptional.isEmpty()) {
			throw new ResourceNotFoundException("Business type not found");
		}
		var businessType = businessTypeOptional.get();

		var existingUserOptional = userRepository.findByPhoneNumber(newUserDto.phoneNumber());
		if (existingUserOptional.isPresent()) {
			throw new ResourceAlreadyExistsException("User with the phone number already exists");
		}

		var hashedPassword = passwordEncoder.encode(newUserDto.password());
		var now = LocalDateTime.now();

		var user = new User();
		user.setFullName(newUserDto.fullName());
		user.setPhoneNumber(newUserDto.phoneNumber());
		user.setPassword(hashedPassword);
		user.setBusinessName(newUserDto.businessName());
		user.setBusinessType(businessType);
		user.setBusinessLocation(newUserDto.businessLocation());
		user.setDateJoined(now);

		userRepository.save(user);

		var newUser = new UserResponseDto(user.getFullName(),
				user.getPhoneNumber(),
				user.getBusinessName(),
				user.getBusinessLocation(),
				user.getDateJoined(),
				businessType
		);

		return new ResponseDto(
				ResponseStatus.success,
				"User account successfully created",
				newUser);
	}
}