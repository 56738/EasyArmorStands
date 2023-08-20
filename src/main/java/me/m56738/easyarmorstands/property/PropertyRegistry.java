package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.type.PropertyType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PropertyRegistry implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<PropertyType, Property> properties = new LinkedHashMap<>();

    public <T> void register(Property<T> property) {
        properties.put(property.getType(), property);
    }

    @Override
    public void forEach(Consumer<Property<?>> consumer) {
        for (Property<?> property : properties.values()) {
            consumer.accept(property);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> getOrNull(PropertyType<T> type) {
        return (Property<T>) properties.get(type);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void commit() {
    }

    public <T> boolean merge(Property<T> property) {
        Property<T> existingProperty = getOrNull(property.getType());
        if (existingProperty != null) {
            return existingProperty.setValue(property.getValue());
        } else {
            register(property);
            return true;
        }
    }
}
