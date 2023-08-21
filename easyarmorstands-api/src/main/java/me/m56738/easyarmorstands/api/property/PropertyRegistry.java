package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A property container which stores instances of properties.
 * <p>
 * Use {@link #register(Property)} to add a property.
 * Properties contained in a registry are typically bound to an element.
 */
public abstract class PropertyRegistry implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<PropertyType, Property> properties = new LinkedHashMap<>();

    public <T> void register(Property<T> property) {
        properties.put(property.getType(), property);
    }

    @Override
    public void forEach(@NotNull Consumer<Property<?>> consumer) {
        for (Property<?> property : properties.values()) {
            consumer.accept(property);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> getOrNull(@NotNull PropertyType<T> type) {
        return (Property<T>) properties.get(type);
    }

    @Override
    public void commit() {
    }
}
