package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.Map;

public class CloneSpawner<T extends Entity> implements EntitySpawner<T> {
    private final EntityType entityType;
    private final Class<T> type;
    private final Map<EntityProperty<T, ?>, Object> properties;

    @SuppressWarnings("unchecked")
    public CloneSpawner(T entity) {
        this.entityType = entity.getType();
        this.type = (Class<T>) entity.getClass();
        this.properties = collectProperties(entity);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Entity> Map<EntityProperty<T, ?>, Object> collectProperties(T entity) {
        Map<EntityProperty<T, ?>, Object> properties = new HashMap<>();
        for (EntityProperty property : EasyArmorStands.getInstance().getEntityPropertyRegistry().getProperties(entity).values()) {
            if (property instanceof EntityLocationProperty) {
                continue;
            }
            Object value = property.getValue(entity);
            properties.put(property, value);
        }
        return properties;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public T spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, type, e -> {
            for (Map.Entry entry : properties.entrySet()) {
                EntityProperty property = (EntityProperty) entry.getKey();
                property.setValue(e, entry.getValue());
            }
        });
    }
}
