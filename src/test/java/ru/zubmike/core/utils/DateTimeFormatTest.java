package ru.zubmike.core.utils;

import org.junit.Assert;
import org.junit.Test;
import ru.zubmike.core.utils.DateTimeFormat;
import ru.zubmike.core.utils.InvalidParameterException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DateTimeFormatTest {

	private static final LocalDate TEST_DATE = LocalDate.of(2019, 9, 13);
	private static final String TEST_DD_MM_YYYY_STRING = "13.09.2019";
	private static final String TEST_ISO_DATE_STRING = "2019-09-13";

	private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2019, 9, 13, 12, 34, 56);
	private static final String TEST_DD_MM_YYYY_HH_MM_SS_STRING = "13.09.2019 12:34:56";
	private static final String TEST_ISO_STRING = "2019-09-13T12:34:56";

	@Test
	public void convertToString() {
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY.convertToString(TEST_DATE), TEST_DD_MM_YYYY_STRING);
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY_HH_MM_SS.convertToString(TEST_DATE_TIME), TEST_DD_MM_YYYY_HH_MM_SS_STRING);
		Assert.assertEquals(DateTimeFormat.ISO.convertToString(TEST_DATE_TIME), TEST_ISO_STRING);
		Assert.assertEquals(DateTimeFormat.ISO_DATE.convertToString(TEST_DATE), TEST_ISO_DATE_STRING);
	}

	@Test
	public void convertToDateTime() {
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY_HH_MM_SS.convertToDateTime(TEST_DD_MM_YYYY_HH_MM_SS_STRING), TEST_DATE_TIME);
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY.convertToDateTime(TEST_DD_MM_YYYY_STRING), TEST_DATE.atStartOfDay());
		Assert.assertEquals(DateTimeFormat.ISO.convertToDateTime(TEST_ISO_STRING), TEST_DATE_TIME);
		Assert.assertEquals(DateTimeFormat.ISO_DATE.convertToDateTime(TEST_ISO_DATE_STRING), TEST_DATE.atStartOfDay());
	}

	@Test
	public void convertToDate() {
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY_HH_MM_SS.convertToDate(TEST_DD_MM_YYYY_HH_MM_SS_STRING), TEST_DATE);
		Assert.assertEquals(DateTimeFormat.DD_MM_YYYY.convertToDate(TEST_DD_MM_YYYY_STRING), TEST_DATE);
		Assert.assertEquals(DateTimeFormat.ISO.convertToDate(TEST_ISO_STRING), TEST_DATE);
		Assert.assertEquals(DateTimeFormat.ISO_DATE.convertToDate(TEST_ISO_DATE_STRING), TEST_DATE);
	}

	@Test
	public void convertNull() {
		Assert.assertNull(DateTimeFormat.ISO.convertToString((LocalDateTime) null));
		Assert.assertNull(DateTimeFormat.ISO.convertToString((ZonedDateTime) null));
		Assert.assertNull(DateTimeFormat.ISO.convertToString((LocalDate) null));
		Assert.assertNull(DateTimeFormat.ISO.convertToDateTime(null));
		Assert.assertNull(DateTimeFormat.ISO.convertToDate(null));
	}


	@Test(expected = InvalidParameterException.class)
	public void convertToDateTimeInvalid() {
		DateTimeFormat.ISO.convertToDateTime(TEST_DD_MM_YYYY_HH_MM_SS_STRING);
	}

	@Test(expected = InvalidParameterException.class)
	public void convertToDateInvalid() {
		DateTimeFormat.ISO_DATE.convertToDateTime(TEST_DD_MM_YYYY_STRING);
	}
}
