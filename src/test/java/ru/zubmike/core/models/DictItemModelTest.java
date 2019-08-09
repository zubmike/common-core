package ru.zubmike.core.models;

import org.junit.Assert;
import org.junit.Test;
import ru.zubmike.core.types.BasicDictItem;
import ru.zubmike.core.types.DictItem;

import java.util.List;
import java.util.stream.Collectors;

public class DictItemModelTest {

	private static final List<DictItem<Integer>> TEST_ITEMS = List.of(
			new BasicDictItem(1, "One"),
			new BasicDictItem(2, "Two"),
			new BasicDictItem(3, null),
			new BasicDictItem(4, "Four"),
			new BasicDictItem(5, "Five")
	);

	@Test
	public void get() {
		var model = new DictItemModelImpl<Integer, DictItem<Integer>>();
		Assert.assertTrue(model.isEmpty());

		model.fill(TEST_ITEMS);

		Assert.assertFalse(model.isEmpty());
		Assert.assertEquals(TEST_ITEMS, model.getAll());

		Assert.assertTrue(model.get(1).isPresent());
		Assert.assertEquals(TEST_ITEMS.get(0), model.get(1).get());
		Assert.assertTrue(model.get(null).isEmpty());
		Assert.assertTrue(model.get(99).isEmpty());

		Assert.assertEquals(TEST_ITEMS.get(1).getName(), model.getName(2));
		Assert.assertNull(model.getName(3));
		Assert.assertEquals("empty", model.getName(3, "empty"));
	}

	@Test
	public void add() {
		var model = new DictItemModelImpl<Integer, DictItem<Integer>>();
		model.fill(TEST_ITEMS);
		model.add(new BasicDictItem(0, "Zero"));
		Assert.assertNotEquals(TEST_ITEMS, model.getAll());
		Assert.assertTrue(model.get(0).isPresent());
	}

	@Test
	public void update() {
		var model = new DictItemModelImpl<Integer, DictItem<Integer>>();
		model.fill(TEST_ITEMS);
		model.update(new BasicDictItem(3, "Three"));
		Assert.assertNotEquals(TEST_ITEMS, model.getAll());
	}


	@Test
	public void remove() {
		var model = new DictItemModelImpl<Integer, DictItem<Integer>>();
		model.fill(TEST_ITEMS);
		model.remove(3);
		Assert.assertFalse(model.get(0).isPresent());
		Assert.assertEquals(
				TEST_ITEMS.stream().filter(item -> item.getId() != 3).map(DictItem::getId).collect(Collectors.toSet()),
				model.getIds());
	}
}
