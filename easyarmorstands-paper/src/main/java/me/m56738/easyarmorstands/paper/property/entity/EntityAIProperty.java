package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.LivingEntity;

public class EntityAIProperty implements Property<Boolean> {
    private final LivingEntity entity;

    public EntityAIProperty(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.AI;
    }

    @Override
    public Boolean getValue() {
        return entity.hasAI();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setAI(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
