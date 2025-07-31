package me.m56738.easyarmorstands.modded.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ArmorStandMarkerProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandMarkerProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.MARKER;
    }

    @Override
    public Boolean getValue() {
        return entity.isMarker();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setMarker(value);
        return true;
    }
}
