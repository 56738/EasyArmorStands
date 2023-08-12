package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.Nullable;

public interface Property<T> {
    static <T> Property<T> of(PropertyType<T> type, T value) {
        return new SimpleProperty<>(type, value);
    }

    static <T> Property<T> immutable(PropertyType<T> type, T value) {
        return new ImmutableProperty<>(type, value);
    }

    static <T> Property<T> immutable(Property<T> property) {
        return Property.immutable(property.getType(), property.getValue());
    }

    PropertyType<T> getType();

    T getValue();

    boolean setValue(T value);

    default @Nullable PendingChange prepareChange(T value) {
        return PendingChange.of(this, value);
    }
}
