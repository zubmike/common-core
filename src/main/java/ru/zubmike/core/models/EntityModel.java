package ru.zubmike.core.models;

import ru.zubmike.core.types.EntityItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EntityModel<I extends Serializable, T extends EntityItem<I>> {

	void fill(@NotNull Collection<T> items);

	void add(@NotNull T item);

	void update(@NotNull T item);

	void remove(@NotNull I id);

	Optional<T> get(@Null I id);

	Set<I> getIds();

	List<T> getAll();

	boolean isEmpty();
}
