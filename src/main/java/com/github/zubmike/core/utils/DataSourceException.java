package com.github.zubmike.core.utils;

import java.io.Serial;

public class DataSourceException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 5930252103520217467L;

	private final DataSourceType type;

	public DataSourceException(DataSourceType type, String message) {
		this(type, message, null);
	}

	public DataSourceException(DataSourceType type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	public DataSourceType getType() {
		return type;
	}

	public enum DataSourceType {
		DB, HTTP, FILE, OTHER
	}
}
