package ru.zubmike.core.models;

import org.junit.Assert;
import org.junit.Test;
import ru.zubmike.core.types.BasicTreeDictItem;
import ru.zubmike.core.types.TreeDictItem;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TreeDictItemModelTest {

	private static final List<TreeDictItem<Integer>> TEST_ITEMS = List.of(
			new BasicTreeDictItem(0, "Zero", null),
			new BasicTreeDictItem(1, "One", 0),
			new BasicTreeDictItem(2, "Two", 0),
			new BasicTreeDictItem(11, "Eleven", 1),
			new BasicTreeDictItem(12, "Twelve", 1)
	);

	@Test
	public void get() {
		var model = new TreeDictModelImpl<Integer, TreeDictItem<Integer>>();
		model.fill(TEST_ITEMS);

		Assert.assertEquals(TEST_ITEMS, model.getAll());
		Assert.assertEquals(Collections.singleton(0), model.getRootIds());
		Assert.assertEquals(Set.of(11, 12), model.getChildrenIds(1));
		Assert.assertEquals(Set.of(1, 2, 11, 12), model.getAllChildrenIds(0));
	}

	@Test
	public void removeLeaf() {
		var model = new TreeDictModelImpl<Integer, TreeDictItem<Integer>>();
		model.fill(TEST_ITEMS);

		model.remove(12);
		Assert.assertFalse(model.get(12).isPresent());
		Assert.assertEquals(Collections.singleton(11), model.getChildrenIds(1));
	}

	@Test
	public void removeNode() {
		var model = new TreeDictModelImpl<Integer, TreeDictItem<Integer>>();
		model.fill(TEST_ITEMS);

		model.remove(1);
		Assert.assertFalse(model.get(1).isPresent());
		Assert.assertTrue(model.getChildrenIds(1).isEmpty());
		Assert.assertEquals(Collections.singleton(2), model.getChildrenIds(0));
	}

	@Test
	public void updateParent() {
		var model = new TreeDictModelImpl<Integer, TreeDictItem<Integer>>();
		model.fill(TEST_ITEMS);

		model.update(new BasicTreeDictItem(12, "Twelve", 2));
		Assert.assertTrue(model.get(12).isPresent());
		Assert.assertEquals(model.get(12).get().getParentId().intValue(), 2);
		Assert.assertEquals(Set.of(11), model.getChildrenIds(1));
		Assert.assertEquals(Set.of(12), model.getChildrenIds(2));
	}
}
