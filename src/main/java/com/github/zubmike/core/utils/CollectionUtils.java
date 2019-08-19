package com.github.zubmike.core.utils;

import javax.validation.constraints.Null;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtils {

	public static boolean isEmpty(@Null Collection items) {
		return items == null || items.isEmpty();
	}

	public static boolean isNotEmpty(@Null Collection items) {
		return !isEmpty(items);
	}

	public static <T> List<T> getPageItems(List<T> items, Comparator<? super T> comparator, Integer page, Integer limit) {
		items.sort(comparator);
		return getPageItems(items, page, limit);
	}

	public static <T> List<T> getPageItems(List<T> items, Integer page, Integer limit) {
		return page != null && page > 0 && limit != null && limit > 0
				? getPageItems(items, page.intValue(), limit.intValue())
				: items;
	}

	public static <T> List<T> getPageItems(List<T> items, int page, int limit) {
		int size = items.size();
		int fromIndex = (page - 1) * limit;
		int toIndex = page * limit;
		if (fromIndex > size) {
			return Collections.emptyList();
		}
		if (toIndex > size) {
			toIndex = size;
		}
		return items.subList(fromIndex, toIndex);
	}

	public static <T> int peekPages(Function<Integer, List<T>> pageGetter, Consumer<List<T>> pagePeek) {
		int total = 0;
		int page = 1;
		List<T> items = pageGetter.apply(page);
		page++;
		while (isNotEmpty(items)) {
			total += items.size();
			pagePeek.accept(items);
			items = pageGetter.apply(page);
			page++;
		}
		return total;
	}

	public static boolean isEmpty(@Null Map map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(@Null Map map) {
		return !isEmpty(map);
	}
}
