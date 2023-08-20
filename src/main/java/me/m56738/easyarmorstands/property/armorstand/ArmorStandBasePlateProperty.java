package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import org.bukkit.entity.ArmorStand;

public class ArmorStandBasePlateProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandBasePlateProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return PropertyTypes.ARMOR_STAND_BASE_PLATE;
    }

    @Override
    public Boolean getValue() {
        return entity.hasBasePlate();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setBasePlate(value);
        return true;
    }
}
