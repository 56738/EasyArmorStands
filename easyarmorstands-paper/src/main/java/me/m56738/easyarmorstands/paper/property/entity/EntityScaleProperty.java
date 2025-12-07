package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

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
        AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
        if (attribute != null) {
            return attribute.getBaseValue();
        } else {
            return 1.0;
        }
    }

    @Override
    public boolean setValue(Double value) {
        AttributeInstance attribute = entity.getAttribute(Attribute.SCALE);
        if (attribute != null) {
            attribute.setBaseValue(value);
            return true;
        } else {
            return false;
        }
    }
}
