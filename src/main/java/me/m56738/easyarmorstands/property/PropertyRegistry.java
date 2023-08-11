package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.PropertyKey;

import java.util.HashMap;
import java.util.Map;

public class PropertyRegistry implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<PropertyKey, Property> properties = new HashMap<>();

    public <T> void register(PropertyKey<T> key, Property<T> property) {
        properties.put(key, property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> get(PropertyKey<T> key) {
        return (Property<T>) properties.get(key);
    }
}
