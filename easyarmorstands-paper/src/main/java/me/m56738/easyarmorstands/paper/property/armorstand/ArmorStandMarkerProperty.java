package me.m56738.easyarmorstands.paper.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;

public class ArmorStandMarkerProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandMarkerProperty(ArmorStand entity) {
        this.entity = entity;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
