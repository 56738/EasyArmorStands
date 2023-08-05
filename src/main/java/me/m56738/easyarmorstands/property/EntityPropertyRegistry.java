package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Entity;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("rawtypes")
@Deprecated
public class EntityPropertyRegistry {
    private final Map<String, LegacyEntityPropertyType<?, ?>> properties = new TreeMap<>();

    public EntityPropertyRegistry() {
    }

    public void register(LegacyEntityPropertyType<?, ?> property) {
        String name = property.getName();
        LegacyEntityPropertyType old = properties.putIfAbsent(name, property);
        if (old != null) {
            throw new IllegalStateException("Duplicate property: " + name);
        }
    }

    public Map<String, LegacyEntityPropertyType<?, ?>> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> Map<String, LegacyEntityPropertyType<? super E, ?>> getProperties(E entity) {
        Class type = entity.getClass();
        Map<String, LegacyEntityPropertyType<? super E, ?>> result = new TreeMap<>();
        for (Map.Entry<String, LegacyEntityPropertyType<?, ?>> entry : properties.entrySet()) {
            LegacyEntityPropertyType property = entry.getValue();
            if (property.getEntityType().isAssignableFrom(type) && property.isSupported(entity)) {
                result.put(entry.getKey(), (LegacyEntityPropertyType<? super E, ?>) property);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <E extends Entity> LegacyEntityPropertyType<E, ?> getProperty(E entity, String name) {
        LegacyEntityPropertyType property = properties.get(name);
        if (property != null && property.getEntityType().isAssignableFrom(entity.getClass()) && property.isSupported(entity)) {
            return (LegacyEntityPropertyType<E, ?>) property;
        } else {
            return null;
        }
    }
}
