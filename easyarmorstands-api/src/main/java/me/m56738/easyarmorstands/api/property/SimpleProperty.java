package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
record SimpleProperty<T>(
        PropertyType<T> type,
        Supplier<T> getter,
        Consumer<T> setter) implements Property<T> {
    @Override
    public PropertyType<T> getType() {
        return type;
    }

    @Override
    public T getValue() {
        return getter.get();
    }

    @Override
    public boolean setValue(T value) {
        setter.accept(value);
        return true;
    }
}
