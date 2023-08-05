package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("rawtypes")
public class EntityPropertyRegistry {
    private final Map<String, EntityProperty<?, ?>> properties = new TreeMap<>();

    public void register(EntityProperty<?, ?> property) {
        String name = property.getName();
        EntityProperty old = properties.putIfAbsent(name, property);
        if (old != null) {
            throw new IllegalStateException("Duplicate property: " + name);
        }
    }

    public Map<String, EntityProperty<?, ?>> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> Map<String, EntityProperty<? super E, ?>> getProperties(E entity) {
        Class type = entity.getClass();
        Map<String, EntityProperty<? super E, ?>> result = new TreeMap<>();
        for (Map.Entry<String, EntityProperty<?, ?>> entry : properties.entrySet()) {
            EntityProperty property = entry.getValue();
            if (property.getEntityType().isAssignableFrom(type) && property.isSupported(entity)) {
                result.put(entry.getKey(), (EntityProperty<? super E, ?>) property);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> EntityProperty<E, ?> getProperty(E entity, String name) {
        EntityProperty property = properties.get(name);
        if (property != null && property.getEntityType().isAssignableFrom(entity.getClass()) && property.isSupported(entity)) {
            return (EntityProperty<E, ?>) property;
        } else {
            return null;
        }
    }
}
