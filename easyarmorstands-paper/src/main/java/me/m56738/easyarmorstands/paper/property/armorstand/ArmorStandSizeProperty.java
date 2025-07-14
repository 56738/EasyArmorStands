package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;

public class ArmorStandSizeProperty implements Property<ArmorStandSize> {
    private final ArmorStand entity;

    public ArmorStandSizeProperty(ArmorStand entity) {
        this.entity = entity;
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
        entity.setSmall(value.isSmall());
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
