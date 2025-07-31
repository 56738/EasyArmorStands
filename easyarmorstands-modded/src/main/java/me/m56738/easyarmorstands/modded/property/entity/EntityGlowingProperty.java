package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.minecraft.world.entity.Entity;

public class EntityGlowingProperty extends EntityProperty<Entity, Boolean> {
    public EntityGlowingProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.GLOWING;
    }

    @Override
    public Boolean getValue() {
        return entity.hasGlowingTag();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setGlowingTag(value);
        return true;
    }
}
