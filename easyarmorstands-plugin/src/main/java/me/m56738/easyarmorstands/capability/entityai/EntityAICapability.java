package me.m56738.easyarmorstands.capability.entityai;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.LivingEntity;

@Capability(name = "Entity AI", optional = true)
public interface EntityAICapability {
    boolean hasAI(LivingEntity entity);

    void setAI(LivingEntity entity, boolean value);
}
