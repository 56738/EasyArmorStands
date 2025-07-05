package me.m56738.easyarmorstands.capability.entitysize.v1_12;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entitysize.EntitySizeCapability;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class EntitySizeCapabilityProvider implements CapabilityProvider<EntitySizeCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("getWidth");
            Entity.class.getMethod("getHeight");
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
    public EntitySizeCapability create(Plugin plugin) {
        return new EntitySizeCapabilityImpl();
    }

    private static class EntitySizeCapabilityImpl implements EntitySizeCapability {
        @Override
        public double getWidth(Entity entity) {
            return entity.getWidth();
        }

        @Override
        public double getHeight(Entity entity) {
            return entity.getHeight();
        }
    }
}
