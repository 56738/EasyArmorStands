package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EntityScaleProperty implements Property<Double> {
    private final LivingEntity entity;

    public EntityScaleProperty(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Double> getType() {
        return EntityPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Double getValue() {
        return entity.getScaleAttribute();
    }

    @Override
    public boolean setValue(@NotNull Double value) {
        entity.setScaleAttribute(value);
        return true;
    }
}
