package me.m56738.easyarmorstands.modded.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.minecraft.world.entity.Entity;

public class EntitySilentProperty extends EntityProperty<Entity, Boolean> {
    public EntitySilentProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.SILENT;
    }

    @Override
    public Boolean getValue() {
        return entity.isSilent();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setSilent(value);
        return true;
    }
}
