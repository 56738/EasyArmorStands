package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandLockProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandLockProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.LOCK;
    }

    @Override
    public Boolean getValue() {
        return entity.disabledSlots != 0;
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.disabledSlots = value ? -1 : 0;
        return true;
    }
}
