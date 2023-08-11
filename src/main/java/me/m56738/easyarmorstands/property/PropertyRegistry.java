package me.m56738.easyarmorstands.property;

import java.util.HashMap;
import java.util.Map;

public abstract class PropertyRegistry implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<PropertyType, Property> properties = new HashMap<>();

    public <T> void register(Property<T> property) {
        properties.put(property.getType(), property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> getOrNull(PropertyType<T> type) {
        return (Property<T>) properties.get(type);
    }
}
