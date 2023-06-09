package com.example.ejemplos;

public class Validate {

	public Validate() {
	}

	public boolean validaDNI(String value) {

		if (value == null)
			return true;
		value = value.toUpperCase();
		if (!value.matches("^\\d{1,8}[A-Z]$"))
			return false;
		return "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(value.substring(0, value.length() - 1)) % 23) == value
				.charAt(value.length() - 1);

	}

}
