package me.m56738.easyarmorstands.capability.lookup.v1_12;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LookupCapabilityProvider implements CapabilityProvider<LookupCapability> {
    @Override
    public boolean isSupported() {
        try {
            Bukkit.class.getMethod("getEntity", UUID.class);
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
    public LookupCapability create(Plugin plugin) {
        return new LookupCapabilityImpl();
    }

    private static class LookupCapabilityImpl implements LookupCapability {
        @Override
        public Entity getEntity(UUID uuid, @Nullable Chunk expectedChunk) {
            return Bukkit.getEntity(uuid);
        }
    }
}
