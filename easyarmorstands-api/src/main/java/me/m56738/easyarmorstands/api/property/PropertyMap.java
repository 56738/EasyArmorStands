package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * A map which associates property types with values.
 * <p>
 * The contained properties are not bound to any element or entity, changing properties always succeeds.
 * This is primarily used to store properties for an element in memory before
 * {@link me.m56738.easyarmorstands.api.element.ElementType#createElement(PropertyContainer) creating} it.
 */
public final class PropertyMap implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<PropertyType, PropertyImpl> properties = new TreeMap<>(Comparator.comparing(PropertyType::key));

    public PropertyMap() {
    }

    public PropertyMap(@NotNull PropertyContainer container) {
        putAll(container);
    }

    public void putAll(@NotNull PropertyContainer container) {
        container.forEach(this::importProperty);
    }

    private <T> void importProperty(@NotNull Property<T> property) {
        put(property.getType(), property.getValue());
    }

    @Override
    public void forEach(@NotNull Consumer<@NotNull Property<?>> consumer) {
        for (PropertyImpl<?> property : properties.values()) {
            consumer.accept(property);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T> Property<T> getOrNull(@NotNull PropertyType<T> type) {
        return properties.get(type);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void commit(@Nullable Component description) {
    }

    @SuppressWarnings("unchecked")
    public <T> boolean put(@NotNull PropertyType<T> type, @NotNull T value) {
        return properties.computeIfAbsent(type, PropertyImpl::new).setValue(value);
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public void remove(PropertyType<?> type) {
        properties.remove(type);
    }

    public void clear() {
        properties.clear();
    }

    private static class PropertyImpl<T> implements Property<T> {
        private final PropertyType<T> type;
        private T value;

        private PropertyImpl(PropertyType<T> type) {
            this.type = type;
        }

        @Override
        public @NotNull PropertyType<T> getType() {
            return type;
        }

        @Override
        public @NotNull T getValue() {
            return type.cloneValue(value);
        }

        @Override
        public boolean setValue(@NotNull T value) {
            Objects.requireNonNull(value);
            this.value = type.cloneValue(value);
            return true;
        }
    }
}
