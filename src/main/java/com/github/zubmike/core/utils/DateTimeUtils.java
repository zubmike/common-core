package com.github.zubmike.core.utils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Set;

public class DateTimeUtils {

	public static final int HOURS_PER_DAY = 24;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
	public static final int MONTH_PER_QUARTER = 3;
	public static final Set<Integer> QUARTERS = Set.of(1, 2, 3, 4 );

	@Null
	public static LocalDateTime getDateTime(@Null String date) {
		return DateTimeFormat.ISO.convertToDateTime(date);
	}

	@Null
	public static LocalDate getDate(@Null String date) {
		return DateTimeFormat.ISO_DATE.convertToDate(date);
	}

	@Null
	public static String toString(@Null LocalDateTime date) {
		return DateTimeFormat.ISO.convertToString(date);
	}

	@Null
	public static String toString(@Null LocalDate date) {
		return DateTimeFormat.ISO_DATE.convertToString(date);
	}

	public static LocalDateTime getStartDateTimeOfQuarter(int year, int quarter) {
		int firstMonth = MONTH_PER_QUARTER * quarter - (MONTH_PER_QUARTER - 1);
		return LocalDateTime.of(year, firstMonth, 1, 0, 0, 0, 0);
	}

	public static LocalDateTime getFinishDateTimeOfQuarter(int year, int quarter) {
		int lastMonth = quarter * MONTH_PER_QUARTER;
		return LocalDateTime
				.of(year, lastMonth, 1, HOURS_PER_DAY - 1, MINUTES_PER_HOUR - 1, SECONDS_PER_MINUTE - 1)
				.with(TemporalAdjusters.lastDayOfMonth());
	}

	public static LocalDate getStartDateOfQuarter(int year, int quarter) {
		return getStartDateTimeOfQuarter(year, quarter).toLocalDate();
	}

	public static LocalDate getFinishDateOfQuarter(int year, int quarter) {
		return getFinishDateTimeOfQuarter(year, quarter).toLocalDate();
	}

	public static int getQuarter(@NotNull LocalDateTime date) {
		return getQuarter(date.toLocalDate());
	}

	public static int getQuarter(@NotNull LocalDate date) {
		return date.getMonth().get(IsoFields.QUARTER_OF_YEAR);
	}

	public static int getWeek(@NotNull LocalDateTime date) {
		return getWeek(date.toLocalDate());
	}

	public static int getWeek(@NotNull LocalDate date) {
		return date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
	}

	public static long getSeconds(@NotNull LocalDateTime date) {
		return date.atZone(ZoneId.systemDefault()).toEpochSecond();
	}
}
