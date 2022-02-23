package com.github.zubmike.core.types;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serial;
import java.util.Objects;

public class BasicDictItem implements DictItem<Integer> {

	@Serial
	private static final long serialVersionUID = 3412493314013523500L;

	private int id;

	private String name;

	public BasicDictItem() {
	}

	public BasicDictItem(int id, @Null String name) {
		this.id = id;
		this.name = name;
	}

	@NotNull
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(@NotNull Integer id) {
		this.id = id;
	}

	@Null
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(@Null String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BasicDictItem that)) {
			return false;
		}
		return id == that.id && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return id + " " + name;
	}
}
