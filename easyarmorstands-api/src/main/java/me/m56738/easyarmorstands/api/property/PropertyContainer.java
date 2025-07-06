package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface PropertyContainer {
    @Contract(pure = true)
    static @NotNull PropertyContainer immutable(@NotNull PropertyContainer container) {
        if (container instanceof ImmutablePropertyContainer) {
            return container;
        }
        return new ImmutablePropertyContainer(container);
    }

    void forEach(@NotNull Consumer<@NotNull Property<?>> consumer);

    @Contract(pure = true)
    <T> @Nullable Property<T> getOrNull(@NotNull PropertyType<T> type);

    @Contract(pure = true)
    default <T> @NotNull Property<T> get(@NotNull PropertyType<T> type) {
        Property<T> property = getOrNull(type);
        if (property == null) {
            throw new UnknownPropertyException(type);
        }
        return property;
    }
}
