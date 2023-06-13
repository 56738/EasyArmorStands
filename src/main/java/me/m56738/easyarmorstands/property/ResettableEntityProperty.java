package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

public interface ResettableEntityProperty<E extends Entity, T> extends EntityProperty<E, T> {
    T getResetValue();
}
