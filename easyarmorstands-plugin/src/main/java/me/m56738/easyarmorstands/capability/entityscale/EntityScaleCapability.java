package me.m56738.easyarmorstands.capability.entityscale;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.LivingEntity;

@Capability(name = "Entity scale", optional = true)
public interface EntityScaleCapability {
    boolean hasScale(LivingEntity entity);

    double getEffectiveScale(LivingEntity entity);

    double getScale(LivingEntity entity);

    void setScale(LivingEntity entity, double scale);

    void resetScale(LivingEntity entity);
}
