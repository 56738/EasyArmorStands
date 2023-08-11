package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.Nullable;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    <T> @Nullable Property<T> get(PropertyType<T> type);
}
