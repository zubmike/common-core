package com.github.zubmike.core.utils;

import java.io.Serial;

public class InternalException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -4395481990562022216L;

	public InternalException() {
	}

	public InternalException(String message) {
		super(message);
	}

	public InternalException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalException(Throwable cause) {
		super(cause);
	}
}
