package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;

public class ArmorStandGravityProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandGravityProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.GRAVITY;
    }

    @Override
    public Boolean getValue() {
        return entity.hasGravity();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setGravity(value);
        return true;
    }
}
