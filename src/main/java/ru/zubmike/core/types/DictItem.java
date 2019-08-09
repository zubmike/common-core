package ru.zubmike.core.types;

import javax.validation.constraints.Null;
import java.io.Serializable;

public interface DictItem <I extends Serializable> extends EntityItem<I> {

	@Null
	String getName();

	void setName(@Null String name);

}
