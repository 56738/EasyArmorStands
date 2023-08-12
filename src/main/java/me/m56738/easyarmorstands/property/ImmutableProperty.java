package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.Nullable;

class ImmutableProperty<T> implements Property<T> {
    private final PropertyType<T> type;
    private final T value;

    ImmutableProperty(PropertyType<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public PropertyType<T> getType() {
        return type;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean setValue(T value) {
        return false;
    }

    @Override
    public @Nullable PendingChange prepareChange(T value) {
        return null;
    }
}
