package me.m56738.easyarmorstands.session;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.function.Consumer;

public interface EntitySpawner<T extends Entity> {
    static <T extends Entity> EntitySpawner<T> of(EntityType type, Consumer<T> configure) {
        return new SimpleEntitySpawner<>(type, configure);
    }

    EntityType getEntityType();

    @SuppressWarnings("unchecked")
    default Class<T> getType() {
        return (Class<T>) getEntityType().getEntityClass();
    }

    T spawn(Location location);
}
