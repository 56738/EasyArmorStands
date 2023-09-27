package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class ImmutableProperty<T> implements Property<T> {
    private final PropertyType<T> type;
    private final T value;

    ImmutableProperty(PropertyType<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public @NotNull PropertyType<T> getType() {
        return type;
    }

    @Override
    public @NotNull T getValue() {
        return value;
    }

    @Override
    public boolean setValue(@NotNull T value) {
        return false;
    }

    @Override
    public @Nullable PendingChange prepareChange(@NotNull T value) {
        return null;
    }
}
