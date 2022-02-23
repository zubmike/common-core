package com.github.zubmike.core.utils;

import java.io.Serial;

public class InvalidParameterException extends IllegalArgumentException {

	@Serial
	private static final long serialVersionUID = 207813802164014340L;

	public InvalidParameterException() {
	}

	public InvalidParameterException(String message) {
		super(message);
	}

	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
