package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.minecraft.world.entity.Mob;

public class EntityAIProperty extends EntityProperty<Mob, Boolean> {
    public EntityAIProperty(Mob entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.AI;
    }

    @Override
    public Boolean getValue() {
        return !entity.isNoAi();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setNoAi(!value);
        return true;
    }
}
