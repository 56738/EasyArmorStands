package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.ArmorStand;

public class ArmorStandCanTickProperty extends EntityProperty<ArmorStand, Boolean> {
    public ArmorStandCanTickProperty(ArmorStand entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.CAN_TICK;
    }

    @Override
    public Boolean getValue() {
        return entity.canTick();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setCanTick(value);
        return true;
    }
}
