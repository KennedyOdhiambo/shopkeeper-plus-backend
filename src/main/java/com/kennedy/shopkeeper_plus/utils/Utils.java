package com.kennedy.shopkeeper_plus.utils;

import com.kennedy.shopkeeper_plus.models.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");


	public static String formatPhoneNumber(String phoneNumber) {
		if (phoneNumber.startsWith("0")) {
			return "254" + phoneNumber.substring(1);
		}
		return phoneNumber;
	}

	public static LocalDate convertToLocalDate(String dateString) {
		return LocalDate.parse(dateString, FORMATTER);
	}

	public static User getCurrentUser() {
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
