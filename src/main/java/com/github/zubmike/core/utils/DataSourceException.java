package com.github.zubmike.core.utils;

public class DataSourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

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
