package com.github.zubmike.core.utils;

import java.io.Serial;

public class NotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 9158587617362154172L;

	public NotFoundException() {
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
