package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
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
    private final Map<PropertyType, PropertyImpl> properties = new LinkedHashMap<>();

    public PropertyMap() {
    }

    public PropertyMap(PropertyContainer container) {
        container.forEach(this::importProperty);
    }

    private <T> void importProperty(Property<T> property) {
        put(property.getType(), property.getValue());
    }

    @Override
    public void forEach(@NotNull Consumer<Property<?>> consumer) {
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
    public void commit() {
    }

    @SuppressWarnings("unchecked")
    public <T> boolean put(PropertyType<T> type, T value) {
        return properties.computeIfAbsent(type, PropertyImpl::new).setValue(value);
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
        public T getValue() {
            return type.cloneValue(value);
        }

        @Override
        public boolean setValue(T value) {
            this.value = type.cloneValue(value);
            return true;
        }
    }
}
