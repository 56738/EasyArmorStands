package me.m56738.easyarmorstands.bukkit.feature.v1_11;

import me.m56738.easyarmorstands.bukkit.feature.EntitySpawner;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public class EntitySpawnerImpl implements EntitySpawner {
    @Override
    public <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure) {
        return location.getWorld().spawn(location, type, configure::accept);
    }

    public static class Provider implements EntitySpawner.Provider {
        @Override
        public boolean isSupported() {
            try {
                Class.forName("org.bukkit.util.Consumer");
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public EntitySpawner create() {
            return new EntitySpawnerImpl();
        }
    }
}
