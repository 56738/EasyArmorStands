package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class PropertyWrapperContainer implements PropertyContainer {
    private final PropertyContainer container;

    protected PropertyWrapperContainer(@NotNull PropertyContainer container) {
        this.container = container;
    }

    protected abstract <T> @NotNull Property<T> wrap(@NotNull Property<T> property);

    private <T> @Nullable Property<T> wrapOrNull(@Nullable Property<T> property) {
        if (property != null) {
            return wrap(property);
        } else {
            return null;
        }
    }

    @Override
    public void forEach(@NotNull Consumer<@NotNull Property<?>> consumer) {
        container.forEach(p -> consumer.accept(wrap(p)));
    }

    @Override
    public @Nullable <T> Property<T> getOrNull(@NotNull PropertyType<T> type) {
        return wrapOrNull(container.getOrNull(type));
    }

    @Override
    public @NotNull <T> Property<T> get(@NotNull PropertyType<T> type) {
        return wrap(container.get(type));
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    @Override
    public void commit(@Nullable Component description) {
        container.commit(description);
    }
}
