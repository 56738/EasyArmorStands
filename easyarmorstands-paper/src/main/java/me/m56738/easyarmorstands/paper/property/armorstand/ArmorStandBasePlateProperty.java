package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;

public class ArmorStandBasePlateProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandBasePlateProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.BASE_PLATE;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
