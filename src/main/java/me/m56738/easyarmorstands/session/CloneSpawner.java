package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class CloneSpawner<T extends Entity> implements EntitySpawner<T> {
    private final EntityType entityType;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public CloneSpawner(T entity) {
        this.entityType = entity.getType();
        this.type = (Class<T>) entity.getClass();
//        this.properties = collectProperties(entity);
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public T spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return spawnCapability.spawnEntity(location, type, e -> {
            // TODO
//            for (Map.Entry entry : properties.entrySet()) {
//                LegacyEntityPropertyType property = (LegacyEntityPropertyType) entry.getKey();
//                property.setValue(e, entry.getValue());
//            }
        });
    }
}
