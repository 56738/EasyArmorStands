package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public abstract class PropertyWrapperContainer implements PropertyContainer {
    private final PropertyContainer container;

    protected PropertyWrapperContainer(PropertyContainer container) {
        this.container = container;
    }

    protected abstract <T> Property<T> wrap(Property<T> property);

    private <T> Property<T> wrapOrNull(Property<T> property) {
        if (property != null) {
            return wrap(property);
        } else {
            return null;
        }
    }

    @Override
    public void forEach(@NotNull Consumer<Property<?>> consumer) {
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
    public void commit() {
        container.commit();
    }
}