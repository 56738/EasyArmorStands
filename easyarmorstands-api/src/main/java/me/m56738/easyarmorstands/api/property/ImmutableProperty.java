package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

class ImmutableProperty<T> implements Property<T> {
    private final PropertyType<T> type;
    private final Property<T> property;

    ImmutableProperty(PropertyType<T> type, Property<T> property) {
        this.type = Objects.requireNonNull(type);
        this.property = Objects.requireNonNull(property);
    }

    @Override
    public @NotNull PropertyType<T> getType() {
        return type;
    }

    @Override
    public @NotNull T getValue() {
        return property.getValue();
    }

    @Override
    public boolean setValue(@NotNull T value) {
        return false;
    }

    @Override
    public @Nullable PendingChange prepareChange(@NotNull T value) {
        return null;
    }

    @Override
    public boolean isValid() {
        return property.isValid();
    }
}
