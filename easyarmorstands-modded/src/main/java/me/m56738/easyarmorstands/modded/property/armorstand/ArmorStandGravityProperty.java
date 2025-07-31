package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

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
        return !entity.isNoGravity();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setNoGravity(!value);
        return true;
    }
}
