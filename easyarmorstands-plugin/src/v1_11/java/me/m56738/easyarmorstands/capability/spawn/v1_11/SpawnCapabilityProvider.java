package me.m56738.easyarmorstands.capability.spawn.v1_11;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class SpawnCapabilityProvider implements CapabilityProvider<SpawnCapability> {
    @Override
    public boolean isSupported() {
        try {
            World.class.getMethod("spawn", Location.class, Class.class, org.bukkit.util.Consumer.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public SpawnCapability create(Plugin plugin) {
        return new SpawnCapabilityImpl();
    }

    private static class SpawnCapabilityImpl implements SpawnCapability {
        @Override
        public <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure) {
            return location.getWorld().spawn(location, type, configure::accept);
        }
    }
}
