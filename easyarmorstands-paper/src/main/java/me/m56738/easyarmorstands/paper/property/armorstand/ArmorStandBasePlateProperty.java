package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;

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
        return entity.hasBasePlate();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setBasePlate(value);
        return true;
    }
}
