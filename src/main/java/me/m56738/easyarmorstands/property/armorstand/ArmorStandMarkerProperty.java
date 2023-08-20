package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.entity.ArmorStand;

public class ArmorStandMarkerProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandMarkerProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return PropertyTypes.ARMOR_STAND_MARKER;
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
