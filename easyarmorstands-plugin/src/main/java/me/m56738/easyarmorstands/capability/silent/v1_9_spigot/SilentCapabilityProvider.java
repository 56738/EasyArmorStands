package me.m56738.easyarmorstands.capability.silent.v1_9_spigot;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.silent.SilentCapability;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class SilentCapabilityProvider implements CapabilityProvider<SilentCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("isSilent");
            Entity.class.getMethod("setSilent", boolean.class);
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
    public SilentCapability create(Plugin plugin) {
        return new SilentCapabilityImpl();
    }

    private static class SilentCapabilityImpl implements SilentCapability {
        @Override
        public boolean isSilent(Entity entity) {
            return entity.isSilent();
        }

        @Override
        public void setSilent(Entity entity, boolean silent) {
            entity.setSilent(silent);
        }
    }
}
