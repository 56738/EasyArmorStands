package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.event.PlayerDestroyEntityEvent;
import me.m56738.easyarmorstands.event.PlayerPreSpawnEntityEvent;
import me.m56738.easyarmorstands.event.PlayerSpawnEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface EntitySpawner<T extends Entity> {
    static <T extends Entity> EntitySpawner<T> of(EntityType type, Consumer<T> configure) {
        return new SimpleEntitySpawner<>(type, configure);
    }

    static <T extends Entity> @Nullable T trySpawn(EntitySpawner<T> spawner, Location location, Player player) {
        PlayerPreSpawnEntityEvent preSpawnEvent = new PlayerPreSpawnEntityEvent(player, location, spawner.getEntityType());
        Bukkit.getPluginManager().callEvent(preSpawnEvent);
        if (preSpawnEvent.isCancelled()) {
            return null;
        }

        T entity = spawner.spawn(location);
        if (entity.getType() != spawner.getEntityType()) {
            entity.remove();
            throw new IllegalArgumentException("Entity type mismatch");
        }

        Bukkit.getPluginManager().callEvent(new PlayerSpawnEntityEvent(player, entity));
        return entity;
    }

    static boolean canRemove(Entity entity, Player player) {
        PlayerDestroyEntityEvent event = new PlayerDestroyEntityEvent(player, entity);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    static boolean tryRemove(Entity entity, Player player) {
        if (!canRemove(entity, player)) {
            return false;
        }
        entity.remove();
        return true;
    }

    EntityType getEntityType();

    @SuppressWarnings("unchecked")
    default Class<T> getType() {
        return (Class<T>) getEntityType().getEntityClass();
    }

    T spawn(Location location);
}
