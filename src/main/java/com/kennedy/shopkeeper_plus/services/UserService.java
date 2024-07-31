package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.user.NewUserDto;
import com.kennedy.shopkeeper_plus.dto.user.UpdatePasswordDto;
import com.kennedy.shopkeeper_plus.dto.user.UpdateUserDto;
import com.kennedy.shopkeeper_plus.dto.user.UserResponseDto;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.enums.Role;
import com.kennedy.shopkeeper_plus.models.User;
import com.kennedy.shopkeeper_plus.repositories.BusinessTypeRepository;
import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.security.JwtService;
import com.kennedy.shopkeeper_plus.utils.ResourceAlreadyExistsException;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import com.kennedy.shopkeeper_plus.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {


	private final UserRepository userRepository;
	private final BusinessTypeRepository businessTypeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public UserService(JwtService jwtService, UserRepository userRepository, BusinessTypeRepository businessTypeRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.businessTypeRepository = businessTypeRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public ResponseDto createUser(NewUserDto newUserDto) {
		var businessType = businessTypeRepository.findById(newUserDto.businessType())
				                   .orElseThrow(() -> new ResourceNotFoundException("Business type not found"));

		var formattedPhoneNumber = Utils.formatPhoneNumber(newUserDto.phoneNumber());

		userRepository.findByPhoneNumberAndStatus(formattedPhoneNumber, EntityStatus.ACTIVE)
				.ifPresent(user -> {
					throw new ResourceNotFoundException("User with the phone number already exists");
				});

		User user = new User();
		user.setFullName(newUserDto.fullName());
		user.setPhoneNumber(formattedPhoneNumber);
		user.setPassword(passwordEncoder.encode(newUserDto.password()));
		user.setBusinessName(newUserDto.businessName());
		user.setBusinessType(businessType);
		user.setBusinessLocation(newUserDto.businessLocation());
		user.setDateJoined(LocalDateTime.now());
		user.setUsername(Utils.formatPhoneNumber(newUserDto.phoneNumber()));
		user.setRole(Role.USER);

		user = userRepository.save(user);
		var accessToken = jwtService.generateAccessToken(user);

		var newUser = new UserResponseDto(
				accessToken,
				user.getFullName(),
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

	public ResponseDto listUsers() {
		var users = userRepository.findAllByStatus(EntityStatus.ACTIVE);

		return new ResponseDto(
				ResponseStatus.success,
				"users",
				users
		);
	}

	public ResponseDto getUserById(UUID userId) {
		var user = userRepository.findByIdAndStatus(userId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		var accessToken = "";

		UserResponseDto userResponseDto = new UserResponseDto(
				accessToken,
				user.getFullName(),
				user.getPhoneNumber(),
				user.getBusinessName(),
				user.getBusinessLocation(),
				user.getDateJoined(),
				user.getBusinessType()
		);

		return new ResponseDto(
				ResponseStatus.success,
				"user",
				userResponseDto
		);
	}

	public ResponseDto updateUser(UpdateUserDto updateUserDto) {

		var user = userRepository.findByIdAndStatus(updateUserDto.id(), EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		var businessType = businessTypeRepository.findByIdAndStatus(updateUserDto.businessType(), EntityStatus.ACTIVE)
				                   .orElseThrow(() -> new ResourceNotFoundException("Business type not found"));

		var existingPhoneNumber = userRepository.findByPhoneNumberAndStatus(updateUserDto.phoneNumber(), EntityStatus.ACTIVE);
		if (existingPhoneNumber.isPresent() && existingPhoneNumber.get().getId() != user.getId()) {
			throw new ResourceAlreadyExistsException("Phone number already registered under different user");
		}

		user.setFullName(updateUserDto.fullName());
		user.setPhoneNumber(Utils.formatPhoneNumber(updateUserDto.phoneNumber()));
		user.setBusinessName(updateUserDto.businessName());
		user.setBusinessType(businessType);
		user.setBusinessLocation(updateUserDto.businessLocation());

		userRepository.save(user);
		var accessToken = "";

		return new ResponseDto(
				ResponseStatus.success,
				"User details successfully updated",
				new UserResponseDto(
						accessToken,
						user.getFullName(),
						user.getPhoneNumber(),
						user.getBusinessName(),
						user.getBusinessLocation(),
						user.getDateJoined(),
						user.getBusinessType()
				)
		);

	}

	public ResponseDto updatePassword(UpdatePasswordDto updatePasswordDto) {

		var user = userRepository.findByIdAndStatus(updatePasswordDto.userId(), EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (passwordEncoder.matches(updatePasswordDto.oldPassword(), user.getPassword())) {
			String newHashedPassword = passwordEncoder.encode(updatePasswordDto.newPassword());
			user.setPassword(newHashedPassword);
			userRepository.save(user);

			return new ResponseDto(
					ResponseStatus.success,
					"password successfully updated",
					null
			);

		}

		return new ResponseDto(
				ResponseStatus.fail,
				"Failed to update, passwords do not match",
				null
		);

	}


	public ResponseDto deleteUser(UUID userId) {
		var user = userRepository.findByIdAndStatus(userId, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		user.setStatus(EntityStatus.DELETED);
		userRepository.save(user);

		return new ResponseDto(
				ResponseStatus.success,
				"user account deleted successfully",
				null
		);
	}


}