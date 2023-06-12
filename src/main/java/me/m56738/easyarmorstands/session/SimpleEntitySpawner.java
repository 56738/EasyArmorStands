package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.function.Consumer;

class SimpleEntitySpawner<T extends Entity> implements EntitySpawner<T> {
    private final EntityType type;
    private final Consumer<T> configure;

    public SimpleEntitySpawner(EntityType type, Consumer<T> configure) {
        this.type = type;
        this.configure = configure;
    }

    @Override
    public EntityType getEntityType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        return (T) spawnCapability.spawnEntity(location, type.getEntityClass(), entity -> configure.accept((T) entity));
    }
}
