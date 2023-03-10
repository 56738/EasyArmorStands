package me.m56738.easyarmorstands.capability.spawn;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

@Capability(name = "Entity spawning")
public interface SpawnCapability {
    <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure);
}
