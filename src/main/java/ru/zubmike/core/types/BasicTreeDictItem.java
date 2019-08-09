package ru.zubmike.core.types;

import javax.validation.constraints.Null;
import java.util.Objects;

public class BasicTreeDictItem extends BasicDictItem implements TreeDictItem<Integer> {

	private static final long serialVersionUID = 1L;

	@Null
	private Integer parentId;

	public BasicTreeDictItem() {

	}

	public BasicTreeDictItem(int id, String name) {
		this(id, name, null);
	}

	public BasicTreeDictItem(int id, String name, Integer parentId) {
		super(id, name);
		this.parentId = parentId;
	}

	@Override
	public Integer getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof BasicTreeDictItem)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		BasicTreeDictItem that = (BasicTreeDictItem) o;
		return Objects.equals(parentId, that.parentId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parentId);
	}

	@Override
	public String toString() {
		return super.toString() + " " + parentId;
	}
}
