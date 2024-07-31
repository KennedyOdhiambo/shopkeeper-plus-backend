package com.kennedy.shopkeeper_plus.services;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.authentication.LoginRequestDTo;
import com.kennedy.shopkeeper_plus.enums.EntityStatus;
import com.kennedy.shopkeeper_plus.enums.ResponseStatus;
import com.kennedy.shopkeeper_plus.repositories.UserRepository;
import com.kennedy.shopkeeper_plus.security.JwtService;
import com.kennedy.shopkeeper_plus.utils.AuthenticationFailedException;
import com.kennedy.shopkeeper_plus.utils.ResourceNotFoundException;
import com.kennedy.shopkeeper_plus.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {


	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public ResponseDto login(LoginRequestDTo loginRequestDTo) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							Utils.formatPhoneNumber(loginRequestDTo.username()),
							loginRequestDTo.password()
					)
			);

			var user = userRepository.findByPhoneNumberAndStatus(loginRequestDTo.username(), EntityStatus.ACTIVE)
					           .orElseThrow(() -> new ResourceNotFoundException("User not found"));
			var accessToken = jwtService.generateAccessToken(user);

			return new ResponseDto(
					ResponseStatus.success,
					"User login was successfull",
					Map.of("access-token", accessToken)

			);
		} catch (BadCredentialsException ex) {
			throw new AuthenticationFailedException("Invalid username or password");
		} catch (AuthenticationException ex) {
			throw new AuthenticationFailedException("Authentication failed");
		}


	}

}
