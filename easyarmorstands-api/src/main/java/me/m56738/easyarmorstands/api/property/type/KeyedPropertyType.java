package me.m56738.easyarmorstands.api.property.type;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface KeyedPropertyType<K, V> {
    @NotNull PropertyType<V> get(@NotNull K key);

    @NotNull Collection<PropertyType<V>> getAll();
}
