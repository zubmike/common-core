package ru.zubmike.core.utils;

public class InternalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

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
