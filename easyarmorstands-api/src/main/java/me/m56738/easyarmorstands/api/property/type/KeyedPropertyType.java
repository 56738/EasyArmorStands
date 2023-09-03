package me.m56738.easyarmorstands.api.property.type;

import org.jetbrains.annotations.NotNull;

public interface KeyedPropertyType<K, V> {
    @NotNull PropertyType<V> get(@NotNull K key);
}
