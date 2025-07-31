package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandSizeProperty extends EntityProperty<ArmorStand, ArmorStandSize> {
    public ArmorStandSizeProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<ArmorStandSize> getType() {
        return ArmorStandPropertyTypes.SIZE;
    }

    @Override
    public ArmorStandSize getValue() {
        return entity.isSmall() ? ArmorStandSize.SMALL : ArmorStandSize.NORMAL;
    }

    @Override
    public boolean setValue(ArmorStandSize value) {
        entity.setSmall(value == ArmorStandSize.SMALL);
        return true;
    }
}
