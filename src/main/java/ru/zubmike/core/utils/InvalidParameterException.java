package ru.zubmike.core.utils;

public class InvalidParameterException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidParameterException() {
	}

	public InvalidParameterException(String message) {
		super(message);
	}
}
