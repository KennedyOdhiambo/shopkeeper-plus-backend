package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.user.NewUserDto;
import com.kennedy.shopkeeper_plus.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user/create")
	public String createUser(
			@Valid
			@RequestBody
			NewUserDto newUser) {
		return userService.createUser(newUser);
	}
}
