package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMarkerProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandMarkerProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.MARKER;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isMarker();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setMarker(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
