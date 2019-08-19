package com.github.zubmike.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class SplitUtilsTest {

	private static final Set<Integer> INT_VALUES = Set.of(1, 2, 3, 4);
	private static final Set<String> STRING_VALUES = Set.of("qwe", "as d", "z x-c", "1");

	@Test
	public void splitInt() {
		Assert.assertEquals(INT_VALUES, SplitUtils.splitInt("1,2,3,4"));
	}

	@Test
	public void splitString() {
		Assert.assertEquals(STRING_VALUES, SplitUtils.splitString("qwe,as d,z x-c,1"));
	}

	@Test
	public void splitNullAndEmptyString() {
		Assert.assertTrue(SplitUtils.splitInt(null).isEmpty());
		Assert.assertTrue(SplitUtils.splitInt("").isEmpty());

		Assert.assertTrue(SplitUtils.splitLong("").isEmpty());
		Assert.assertTrue(SplitUtils.splitLong(null).isEmpty());

		Assert.assertTrue(SplitUtils.splitString(null).isEmpty());
		Assert.assertTrue(SplitUtils.splitString("").isEmpty());
	}

	@Test(expected = InvalidParameterException.class)
	public void splitNumberInvalidString() {
		SplitUtils.splitInt("1,abc,3");
	}

	@Test(expected = InvalidParameterException.class)
	public void splitLongInvalidString() {
		SplitUtils.splitInt("1,abc,3");
	}
}
