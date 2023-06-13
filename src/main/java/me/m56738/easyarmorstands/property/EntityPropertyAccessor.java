package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

public interface EntityPropertyAccessor<E extends Entity, T> {
    T getValue(E entity);

    void setValue(E entity, T value);
}
