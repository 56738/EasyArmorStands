package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

@Deprecated
public interface ResettableEntityProperty<E extends Entity, T> extends LegacyEntityPropertyType<E, T> {
    T getResetValue();
}
