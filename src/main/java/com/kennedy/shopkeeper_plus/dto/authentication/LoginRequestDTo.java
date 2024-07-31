package com.kennedy.shopkeeper_plus.dto.authentication;

public record LoginRequestDTo(

		String username,

		String password
) {
	public String sanitizedToString() {
		return "LoginRequestDto{" +
				       "phoneNumber='" + username + '\'' +
				       ", password='[PROTECTED]'" +
				       '}';
	}
}
