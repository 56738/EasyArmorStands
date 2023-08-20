package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.entity.Entity;

public class EntityCustomNameVisibleProperty implements Property<Boolean> {
    private final Entity entity;

    public EntityCustomNameVisibleProperty(Entity entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return PropertyTypes.ENTITY_CUSTOM_NAME_VISIBLE;
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
