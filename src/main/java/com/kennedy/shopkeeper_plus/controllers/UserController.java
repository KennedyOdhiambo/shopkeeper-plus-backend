package com.kennedy.shopkeeper_plus.controllers;

import com.kennedy.shopkeeper_plus.dto.ResponseDto;
import com.kennedy.shopkeeper_plus.dto.user.NewUserDto;
import com.kennedy.shopkeeper_plus.dto.user.UpdatePasswordDto;
import com.kennedy.shopkeeper_plus.dto.user.UpdateUserDto;
import com.kennedy.shopkeeper_plus.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;


	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public ResponseDto createUser(
			@Valid @RequestBody NewUserDto newUser) {
		return userService.createUser(newUser);
	}

	@GetMapping("/list")
	public ResponseDto listUsers() {
		return userService.listUsers();
	}

	@GetMapping("/{id}")
	public ResponseDto getUserById(
			@PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@PatchMapping("/update")
	public ResponseDto updateUser(
			@Valid @RequestBody UpdateUserDto updateUserDto) {
		return userService.updateUser(updateUserDto);
	}

	@PatchMapping("/update-password")
	public ResponseDto updatePassword(
			@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
		return userService.updatePassword(updatePasswordDto);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseDto deleteUser(
			@PathVariable UUID id) {
		return userService.deleteUser(id);
	}

}
