package ru.zubmike.core.models;

import ru.zubmike.core.types.EntityItem;
import ru.zubmike.core.utils.CollectionUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.*;

public class EntityModelImpl<I extends Serializable, T extends EntityItem<I>> implements EntityModel<I, T> {

	private final Map<I, T> itemMap;

	public EntityModelImpl() {
		this(new LinkedHashMap<>());
	}

	public EntityModelImpl(Map<I, T> itemMap) {
		this.itemMap = itemMap;
	}

	@Override
	public void fill(Collection<T> items) {
		items.forEach(this::add);
	}

	@Override
	public void add(@NotNull T item) {
		itemMap.put(item.getId(), item);
	}

	@Override
	public void update(@NotNull T item) {
		itemMap.replace(item.getId(), item);
	}

	@Override
	public void remove(@NotNull I id) {
		itemMap.remove(id);
	}

	@Override
	public Optional<T> get(@Null I id) {
		return Optional.ofNullable(id != null ? itemMap.get(id) : null);
	}

	@Override
	public Set<I> getIds() {
		return new LinkedHashSet<>(itemMap.keySet());
	}

	@Override
	public List<T> getAll() {
		return new ArrayList<>(itemMap.values());
	}

	@Override
	public boolean isEmpty() {
		return CollectionUtils.isEmpty(itemMap);
	}
}
