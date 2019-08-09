package ru.zubmike.core.utils;

import org.junit.Assert;
import org.junit.Test;
import ru.zubmike.core.utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtilsTest {

	@Test
	public void processDateTime() {
		var dateTime = LocalDateTime.now();
		var isoDateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
		Assert.assertEquals(dateTime, DateTimeUtils.getDateTime(isoDateTime));
		Assert.assertEquals(isoDateTime, DateTimeUtils.toString(dateTime));
	}

	@Test
	public void getQuarter() {
		Assert.assertEquals(1, DateTimeUtils.getQuarter(LocalDate.of(2019, 1, 1)));
		Assert.assertEquals(2, DateTimeUtils.getQuarter(LocalDateTime.of(2019, 4, 15, 12, 34, 56)));
		Assert.assertEquals(4, DateTimeUtils.getQuarter(LocalDate.of(2019, 12, 31)));
	}

	@Test
	public void getWeek() {
		Assert.assertEquals(1, DateTimeUtils.getWeek(LocalDate.of(2019, 1, 1)));
		Assert.assertEquals(32, DateTimeUtils.getWeek(LocalDate.of(2019, 8, 8)));
		Assert.assertEquals(52, DateTimeUtils.getWeek(LocalDate.of(2019, 12, 29)));
		Assert.assertEquals(1, DateTimeUtils.getWeek(LocalDate.of(2019, 12, 31)));
	}

	@Test
	public void getStartOfQuarter() {
		Assert.assertEquals(LocalDate.of(2019, 1, 1),
				DateTimeUtils.getStartDateOfQuarter(2019, 1));
		Assert.assertEquals(LocalDateTime.of(2019, 1, 1, 0 , 0, 0),
				DateTimeUtils.getStartDateTimeOfQuarter(2019, 1));
		Assert.assertEquals(LocalDate.of(2019, 7, 1),
				DateTimeUtils.getStartDateOfQuarter(2019, 3));
		Assert.assertEquals(LocalDateTime.of(2019, 7, 1, 0 , 0, 0),
				DateTimeUtils.getStartDateTimeOfQuarter(2019, 3));
	}

	@Test
	public void getFinishOfQuarter() {
		Assert.assertEquals(LocalDate.of(2019, 3, 31),
				DateTimeUtils.getFinishDateOfQuarter(2019, 1));
		Assert.assertEquals(LocalDateTime.of(2019, 3, 31, 23 , 59, 59),
				DateTimeUtils.getFinishDateTimeOfQuarter(2019, 1));
		Assert.assertEquals(LocalDate.of(2019, 12, 31),
				DateTimeUtils.getFinishDateOfQuarter(2019, 4));
		Assert.assertEquals(LocalDateTime.of(2019, 12, 31, 23 , 59, 59),
				DateTimeUtils.getFinishDateTimeOfQuarter(2019, 4));
	}

}
