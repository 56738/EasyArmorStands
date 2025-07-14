package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;

public class ArmorStandCanTickProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandCanTickProperty(ArmorStand entity) {
        this.entity = entity;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
