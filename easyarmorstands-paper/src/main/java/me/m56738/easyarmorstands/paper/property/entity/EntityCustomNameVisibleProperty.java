package me.m56738.easyarmorstands.paper.property.entity;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Entity;

public class EntityCustomNameVisibleProperty extends EntityProperty<Entity, Boolean> {
    public EntityCustomNameVisibleProperty(Entity entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.CUSTOM_NAME_VISIBLE;
    }

    @Override
    public Boolean getValue() {
        return entity.isCustomNameVisible();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setCustomNameVisible(value);
        return true;
    }
}
