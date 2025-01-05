package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.entityai.EntityAICapability;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityAIProperty implements Property<Boolean> {
    private final LivingEntity entity;
    private final EntityAICapability capability;

    public EntityAIProperty(LivingEntity entity, EntityAICapability capability) {
        this.entity = entity;
        this.capability = capability;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return EntityPropertyTypes.AI;
    }

    @Override
    public @NotNull Boolean getValue() {
        return capability.hasAI(entity);
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        capability.setAI(entity, value);
        return true;
    }
}
