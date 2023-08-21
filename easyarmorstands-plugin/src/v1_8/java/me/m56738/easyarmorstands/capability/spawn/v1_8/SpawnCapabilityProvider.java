package me.m56738.easyarmorstands.capability.spawn.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class SpawnCapabilityProvider implements CapabilityProvider<SpawnCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public SpawnCapability create(Plugin plugin) {
        return new SpawnCapabilityImpl();
    }

    private static class SpawnCapabilityImpl implements SpawnCapability {
        @Override
        public <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure) {
            T entity = location.getWorld().spawn(location, type);
            configure.accept(entity);
            return entity;
        }
    }
}
