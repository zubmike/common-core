package com.github.zubmike.core.models;

import com.github.zubmike.core.types.DictItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

public interface DictItemModel<I extends Serializable, T extends DictItem<I>> extends EntityModel<I, T> {

	@Null
	String getName(@Null I id);

	@NotNull
	String getName(@Null I id, String defaultValue);

}
