package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandArmsProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandArmsProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.ARMS;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.hasArms();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setArms(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
