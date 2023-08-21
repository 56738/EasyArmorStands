package me.m56738.easyarmorstands.capability.glow.v1_9;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class GlowCapabilityProvider implements CapabilityProvider<GlowCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getDeclaredMethod("isGlowing");
            Entity.class.getDeclaredMethod("setGlowing", boolean.class);
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
    public GlowCapability create(Plugin plugin) {
        return new GlowCapabilityImpl();
    }

    private static class GlowCapabilityImpl implements GlowCapability {
        @Override
        public boolean isGlowing(Entity entity) {
            return entity.isGlowing();
        }

        @Override
        public void setGlowing(Entity entity, boolean glowing) {
            entity.setGlowing(glowing);
        }
    }
}
