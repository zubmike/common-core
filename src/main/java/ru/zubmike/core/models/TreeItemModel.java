package ru.zubmike.core.models;

import ru.zubmike.core.types.TreeEntityItem;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Set;

public interface TreeItemModel<I extends Serializable, T extends TreeEntityItem<I>> extends EntityModel<I, T> {

	Set<I> getRootIds();

	Set<I> getChildrenIds(@Null I parentId);

	Set<I> getAllChildrenIds(@Null I parentId);

}
