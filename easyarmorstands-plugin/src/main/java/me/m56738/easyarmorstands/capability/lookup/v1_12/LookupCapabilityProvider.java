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
import java.util.regex.Pattern;

public class LookupCapabilityProvider implements CapabilityProvider<LookupCapability> {
    // Paper 1.9 getEntity doesn't seem to work
    private static final Pattern EXCLUDED_VERSION_PATTERN = Pattern.compile("^1\\.9(\\.\\d)?-.*$");

    @Override
    public boolean isSupported() {
        try {
            Bukkit.class.getMethod("getEntity", UUID.class);
            return !EXCLUDED_VERSION_PATTERN.matcher(Bukkit.getBukkitVersion()).matches();
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
