package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.entityscale.EntityScaleCapability;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityScaleProperty implements Property<Double> {
    private final LivingEntity entity;
    private final EntityScaleCapability capability;

    public EntityScaleProperty(LivingEntity entity, EntityScaleCapability capability) {
        this.entity = entity;
        this.capability = capability;
    }

    @Override
    public @NotNull PropertyType<Double> getType() {
        return EntityPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Double getValue() {
        return capability.getScale(entity);
    }

    @Override
    public boolean setValue(@NotNull Double value) {
        if (!capability.hasScale(entity)) {
            return false;
        }
        capability.setScale(entity, value);
        return true;
    }
}
