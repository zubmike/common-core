package ru.zubmike.core.models;

import ru.zubmike.core.types.DictItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DictItemModelImpl<I extends Serializable, T extends DictItem<I>> extends EntityModelImpl<I, T>
		implements DictItemModel<I, T> {

	public DictItemModelImpl() {
		this(new LinkedHashMap<>());
	}

	public DictItemModelImpl(Map<I, T> itemMap) {
		super(itemMap);
	}

	@Null
	@Override
	public String getName(@Null I id) {
		return get(id).map(DictItem::getName).orElse(null);
	}

	@NotNull
	@Override
	public String getName(@Null I id, String defaultValue) {
		return get(id).map(DictItem::getName).orElse(defaultValue);
	}
}
