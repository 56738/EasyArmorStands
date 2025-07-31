package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandArmsProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandArmsProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.ARMS;
    }

    @Override
    public Boolean getValue() {
        return entity.showArms();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setShowArms(value);
        return true;
    }
}
