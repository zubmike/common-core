package ru.zubmike.core.utils;

import org.junit.Assert;
import org.junit.Test;
import ru.zubmike.core.utils.CollectionUtils;

import java.util.*;

public class CollectionUtilsTest {

	private static final List<Integer> TEST_LIST = List.of(0, 1, 2, 3, 4, 5, 6);

	@SuppressWarnings("ConstantConditions")
	@Test
	public void checkEmpty() {
		Assert.assertTrue(CollectionUtils.isEmpty((List) null));
		Assert.assertTrue(CollectionUtils.isEmpty(new ArrayList()));
	}

	@Test
	public void checkNotEmpty() {
		Assert.assertTrue(CollectionUtils.isNotEmpty(TEST_LIST));
	}

	@Test
	public void getPage() {
		List<Integer> page = CollectionUtils.getPageItems(TEST_LIST, 2, 2);
		Assert.assertEquals(List.of(2, 3), page);
	}

	@Test
	public void getPageAll() {
		List<Integer> page = CollectionUtils.getPageItems(TEST_LIST, null, null);
		Assert.assertEquals(TEST_LIST, page);
	}

	@Test
	public void getPageAll2() {
		List<Integer> page = CollectionUtils.getPageItems(TEST_LIST, 1, 99);
		Assert.assertEquals(TEST_LIST, page);
	}

	@Test
	public void getEmptyPage() {
		List<Integer> page = CollectionUtils.getPageItems(TEST_LIST, 99, 99);
		Assert.assertEquals(Collections.emptyList(), page);
	}
}
