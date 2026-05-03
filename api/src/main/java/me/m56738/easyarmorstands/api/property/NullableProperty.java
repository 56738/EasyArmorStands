package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
record NullableProperty<T>(
        PropertyType<Optional<T>> type,
        Supplier<@Nullable T> getter,
        Consumer<@Nullable T> setter) implements Property<Optional<T>> {
    @Override
    public PropertyType<Optional<T>> getType() {
        return type;
    }

    @Override
    public Optional<T> getValue() {
        return Optional.ofNullable(getter.get());
    }

    @Override
    public boolean setValue(Optional<T> value) {
        setter.accept(value.orElse(null));
        return true;
    }
}
