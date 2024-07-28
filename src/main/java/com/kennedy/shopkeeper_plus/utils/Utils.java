package com.kennedy.shopkeeper_plus.utils;

public class Utils {

	public static String formatPhoneNumber(String phoneNumber) {
		if (phoneNumber.startsWith("0")) {
			return "254" + phoneNumber.substring(1);
		}
		return phoneNumber;
	}
}
