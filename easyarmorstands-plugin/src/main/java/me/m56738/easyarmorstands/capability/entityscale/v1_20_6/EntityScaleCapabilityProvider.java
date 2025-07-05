package me.m56738.easyarmorstands.capability.entityscale.v1_20_6;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entityscale.EntityScaleCapability;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class EntityScaleCapabilityProvider implements CapabilityProvider<EntityScaleCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public EntityScaleCapability create(Plugin plugin) {
        return new EntityScaleCapabilityImpl();
    }

    private static class EntityScaleCapabilityImpl implements EntityScaleCapability {
        @Override
        public boolean hasScale(LivingEntity entity) {
            return entity.getAttribute(Attribute.SCALE) != null;
        }

        @Override
        public double getEffectiveScale(LivingEntity entity) {
            AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
            if (attribute == null) {
                return 1;
            }
            return attribute.getValue();
        }

        @Override
        public double getScale(LivingEntity entity) {
            AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
            if (attribute == null) {
                return 1;
            }
            return attribute.getBaseValue();
        }

        @Override
        public void setScale(LivingEntity entity, double scale) {
            AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
            if (attribute == null) {
                return;
            }
            attribute.setBaseValue(scale);
        }

        @Override
        public void resetScale(LivingEntity entity) {
            AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
            if (attribute == null) {
                return;
            }
            attribute.setBaseValue(attribute.getDefaultValue());
        }
    }
}
