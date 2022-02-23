package com.github.zubmike.core.utils;

import com.github.zubmike.core.types.BasicDictItem;
import com.github.zubmike.core.types.DictItem;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class DictItemUtils {

	public static <T extends DictItem<Integer>> List<BasicDictItem> createBasicDictItems(Collection<T> items) {
		return items.stream()
				.map(DictItemUtils::createBasicDictItem)
				.sorted(Comparator.comparing(DictItem::getId))
				.toList();
	}

	public static <T extends DictItem<Integer>> List<BasicDictItem> createBasicDictItemsSortByName(Collection<T> items) {
		return items.stream()
				.map(DictItemUtils::createBasicDictItem)
				.sorted(Comparator.comparing(DictItem::getName, Comparator.nullsFirst(Comparator.naturalOrder())))
				.toList();
	}

	public static <T extends DictItem<Integer>> BasicDictItem createBasicDictItem(T item) {
		return new BasicDictItem(item.getId(), item.getName());
	}

}
