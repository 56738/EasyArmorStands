package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.Key;

import java.util.HashMap;
import java.util.Map;

public class PropertyRegistry implements PropertyContainer {
    @SuppressWarnings("rawtypes")
    private final Map<Key, Property> properties = new HashMap<>();

    public <T extends Property<?>> void register(Key<T> key, T property) {
        properties.put(key, property);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Property<?>> T get(Key<T> key) {
        return (T) properties.get(key);
    }
}
