package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    <T> @Nullable Property<T> getOrNull(PropertyType<T> type);

    default <T> @NotNull Property<T> get(PropertyType<T> type) {
        Property<T> property = getOrNull(type);
        if (property == null) {
            throw new NullPointerException("Property not found: " + type);
        }
        return property;
    }
}
