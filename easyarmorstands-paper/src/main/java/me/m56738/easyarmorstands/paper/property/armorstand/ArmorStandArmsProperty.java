package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;

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
        return entity.hasArms();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setArms(value);
        return true;
    }
}
