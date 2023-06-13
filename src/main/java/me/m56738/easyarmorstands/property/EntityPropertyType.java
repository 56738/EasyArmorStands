package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityPropertyType<T> extends PropertyType<T> {
    @Nullable Property<T> bind(Entity entity);
}
