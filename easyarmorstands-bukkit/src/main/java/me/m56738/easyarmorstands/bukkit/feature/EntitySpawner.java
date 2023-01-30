package me.m56738.easyarmorstands.bukkit.feature;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public interface EntitySpawner {
    <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure);

    interface Provider extends FeatureProvider<EntitySpawner> {
    }

    class Fallback implements EntitySpawner, Provider {
        @Override
        public <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure) {
            T entity = location.getWorld().spawn(location, type);
            configure.accept(entity);
            return entity;
        }

        @Override
        public boolean isSupported() {
            return true;
        }

        @Override
        public Priority getPriority() {
            return Priority.FALLBACK;
        }

        @Override
        public EntitySpawner create() {
            return this;
        }
    }
}
