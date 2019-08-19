package com.github.zubmike.core.types;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public interface EntityItem<I extends Serializable> extends Serializable {

	@NotNull
	I getId();

	void setId(@NotNull I id);

}
