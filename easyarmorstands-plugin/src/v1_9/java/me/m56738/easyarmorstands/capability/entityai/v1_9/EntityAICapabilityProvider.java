package me.m56738.easyarmorstands.capability.entityai.v1_9;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entityai.EntityAICapability;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class EntityAICapabilityProvider implements CapabilityProvider<EntityAICapability> {
    @Override
    public boolean isSupported() {
        try {
            LivingEntity.class.getMethod("hasAI");
            LivingEntity.class.getMethod("setAI", boolean.class);
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
    public EntityAICapability create(Plugin plugin) {
        return new EntityAICapabilityImpl();
    }

    private static class EntityAICapabilityImpl implements EntityAICapability {
        @Override
        public boolean hasAI(LivingEntity entity) {
            return entity.hasAI();
        }

        @Override
        public void setAI(LivingEntity entity, boolean value) {
            entity.setAI(value);
        }
    }
}
