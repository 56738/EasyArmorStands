package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

@Deprecated
public class EntityPropertyBinding<E extends Entity, T> implements Property<T> {
    private final EntityPropertyType<T> type;
    private final E entity;
    private final EntityPropertyAccessor<E, T> accessor;

    @SuppressWarnings("PatternValidation")
    public EntityPropertyBinding(EntityPropertyType<T> type, E entity, EntityPropertyAccessor<E, T> accessor) {
        this.entity = entity;
        this.type = type;
        this.accessor = accessor;
    }

    @Override
    public PropertyType<T> getType() {
        return type;
    }

    @Override
    public T getValue() {
        return accessor.getValue(entity);
    }

    @Override
    public boolean setValue(T value) {
        accessor.setValue(entity, value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
