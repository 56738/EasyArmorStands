package me.m56738.easyarmorstands.capability.lookup.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LookupCapabilityProvider implements CapabilityProvider<LookupCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public LookupCapability create(Plugin plugin) {
        return new LookupCapabilityImpl();
    }

    private static class LookupCapabilityImpl implements LookupCapability {
        @Override
        public Entity getEntity(UUID uuid, @Nullable Chunk expectedChunk) {
            if (expectedChunk != null) {
                for (Entity entity : expectedChunk.getEntities()) {
                    if (entity.getUniqueId().equals(uuid)) {
                        return entity;
                    }
                }
            }

            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity.getUniqueId().equals(uuid)) {
                        return entity;
                    }
                }
            }
            return null;
        }
    }
}
