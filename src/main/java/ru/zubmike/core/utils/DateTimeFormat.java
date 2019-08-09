package ru.zubmike.core.utils;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public enum DateTimeFormat {

	DD_MM_YYYY("dd.MM.yyyy", false),
	ISO_DATE(DateTimeFormatter.ISO_DATE, false),

	DD_MM_YYYY_HH_MM("dd.MM.yyyy HH:mm", true),
	DD_MM_YYYY_HH_MM_SS("dd.MM.yyyy HH:mm:ss", true),
	ISO(DateTimeFormatter.ISO_DATE_TIME, true);

	private final DateTimeFormatter formatter;
	private final boolean hasTime;

	DateTimeFormat(DateTimeFormatter formatter, boolean hasTime) {
		this.formatter = formatter;
		this.hasTime = hasTime;
	}

	DateTimeFormat(String pattern, boolean hasTime) {
		this.formatter = DateTimeFormatter.ofPattern(pattern);
		this.hasTime = hasTime;
	}

	@Null
	public String convertToString(@Null LocalDateTime date) {
		return date != null ? date.format(formatter) : null;
	}

	@Null
	public String convertToString(@Null ZonedDateTime date) {
		return date != null ? date.format(formatter) : null;
	}

	@Null
	public String convertToString(@Null LocalDate date) {
		return date != null ? date.format(formatter) : null;
	}

	@Null
	public LocalDateTime convertToDateTime(@Null String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return hasTime
					? LocalDateTime.parse(value, formatter)
					: LocalDate.parse(value, formatter).atStartOfDay();
		} catch (DateTimeParseException e) {
			throw new InvalidParameterException();
		}
	}

	@Null
	public LocalDate convertToDate(@Null String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return hasTime
					? LocalDateTime.parse(value, formatter).toLocalDate()
					: LocalDate.parse(value, formatter);
		} catch (DateTimeParseException e) {
			throw new InvalidParameterException();
		}
	}

}