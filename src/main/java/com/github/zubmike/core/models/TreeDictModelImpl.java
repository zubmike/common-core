package com.github.zubmike.core.models;

import com.github.zubmike.core.types.TreeDictItem;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.*;

public class TreeDictModelImpl<I extends Serializable, T extends TreeDictItem<I>>
		extends DictItemModelImpl<I, T> implements TreeDictItemModel<I, T> {

	private final Map<I, Set<I>> parentMultimap;
	private final Set<I> rootIds;

	public TreeDictModelImpl() {
		this(new LinkedHashMap<>(), new LinkedHashMap<>(), new LinkedHashSet<>());
	}

	public TreeDictModelImpl(Map<I, T> itemMap, Map<I, Set<I>> parentMultimap, Set<I> rootIds) {
		super(itemMap);
		this.parentMultimap = parentMultimap;
		this.rootIds = rootIds;
	}

	@Override
	public void fill(Collection<T> items) {
		super.fill(items);
	}

	@Override
	public void add(T item) {
		super.add(item);
		addRelation(item);
	}

	@Override
	public void remove(I id) {
		super.remove(id);
		removeAllRelation(id);
	}

	@Override
	public void update(T item) {
		I id = item.getId();
		Optional<T> prevItemOpt = get(id);
		if (prevItemOpt.isPresent()) {
			super.update(item);
			T prevItem = prevItemOpt.get();
			updateRelation(prevItem, item);
		}
	}

	protected void addRelation(T item) {
		I id = item.getId();
		I parentId = item.getParentId();
		if (parentId == null || id.equals(parentId)) {
			rootIds.add(id);
		} else {
			parentMultimap.computeIfAbsent(parentId, k -> new LinkedHashSet<>()).add(id);
		}
	}

	protected void updateRelation(T prevItem, T item) {
		if (!Objects.equals(prevItem.getParentId(), item.getParentId())) {
			removeRelation(prevItem);
			addRelation(item);
		}
	}

	protected void removeRelation(T item) {
		I id = item.getId();
		I parentId = item.getParentId();
		if (parentId == null || id.equals(parentId)) {
			rootIds.remove(id);
		} else {
			parentMultimap.getOrDefault(parentId, Collections.emptySet()).remove(id);
		}
	}

	protected void removeAllRelation(I id) {
		rootIds.remove(id);
		parentMultimap.values().forEach(ids -> ids.remove(id));
		parentMultimap.remove(id);
	}

	@Override
	public Set<I> getRootIds() {
		return new LinkedHashSet<>(rootIds);
	}

	@Override
	public Set<I> getChildrenIds(@Null I parentId) {
		if (parentId == null) {
			return Collections.emptySet();
		}
		return new LinkedHashSet<>(parentMultimap.getOrDefault(parentId, Collections.emptySet()));
	}

	@Override
	public Set<I> getAllChildrenIds(@Null I parentId) {
		if (parentId == null) {
			return Collections.emptySet();
		}
		Set<I> ids = new LinkedHashSet<>();
		for (I id : parentMultimap.getOrDefault(parentId, Collections.emptySet())) {
			ids.add(id);
			ids.addAll(getAllChildrenIds(id));
		}
		return ids;
	}
}
