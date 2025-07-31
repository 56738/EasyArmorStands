package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EntityScaleProperty extends EntityProperty<LivingEntity, Double> {
    public EntityScaleProperty(LivingEntity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Double> getType() {
        return EntityPropertyTypes.SCALE;
    }

    @Override
    public Double getValue() {
        return (double) entity.getScale();
    }

    @Override
    public boolean setValue(Double value) {
        AttributeInstance attribute = entity.getAttribute(Attributes.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(value);
            return true;
        }
        return false;
    }
}
