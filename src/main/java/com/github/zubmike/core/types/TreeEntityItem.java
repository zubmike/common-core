package com.github.zubmike.core.types;

import javax.validation.constraints.Null;
import java.io.Serializable;

public interface TreeEntityItem<I extends Serializable> extends EntityItem<I> {

	@Null
	I getParentId();

	void setParentId(@Null I id);

}
