package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.Nullable;

public interface Property<T> {
    PropertyType<T> getType();

    T getValue();

    boolean setValue(T value);

    default @Nullable PendingChange prepareChange(T value) {
        return PendingChange.of(this, value);
    }
}
