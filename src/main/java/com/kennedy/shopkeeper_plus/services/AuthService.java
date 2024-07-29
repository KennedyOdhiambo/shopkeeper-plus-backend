package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.authentication.LoginRequestDTo;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import com.kennedy.shopkeeper_plus.utils.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public ResponseDto login(LoginRequestDTo loginRequestDTo) {
		var formattedPhoneNumber = Utils.formatPhoneNumber(loginRequestDTo.phoneNumber());

		var user = userRepository.findByPhoneNumberAndStatus(formattedPhoneNumber, EntityStatus.ACTIVE)
				           .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (!passwordEncoder.matches(loginRequestDTo.password(), user.getPassword())) {
			return new ResponseDto(
					ResponseStatus.fail,
					"Invalid password",
					null
			);
		}

		return new ResponseDto(
				ResponseStatus.success,
				"login successful",
				user
		);

	}
}
