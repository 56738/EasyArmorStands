package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandVisibilityProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandVisibilityProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return EntityPropertyTypes.VISIBLE;
    }

    @Override
    public Boolean getValue() {
        return entity.isInvisible();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setInvisible(value);
        return true;
    }
}
