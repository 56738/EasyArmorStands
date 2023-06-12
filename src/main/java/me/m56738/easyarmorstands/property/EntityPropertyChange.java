package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

public class EntityPropertyChange<E extends Entity, T> {
    private final E entity;
    private final EntityProperty<E, T> property;
    private final T value;

    public EntityPropertyChange(E entity, EntityProperty<E, T> property, T value) {
        this.entity = entity;
        this.property = property;
        this.value = value;
    }

    public E getEntity() {
        return entity;
    }

    public EntityProperty<E, T> getProperty() {
        return property;
    }

    public T getValue() {
        return value;
    }
}
