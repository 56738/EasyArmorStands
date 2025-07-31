package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandBasePlateProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandBasePlateProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.BASE_PLATE;
    }

    @Override
    public Boolean getValue() {
        return entity.showBasePlate();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setNoBasePlate(!value);
        return true;
    }
}
