package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandSizeProperty implements Property<ArmorStandSize> {
    private final ArmorStand entity;

    public ArmorStandSizeProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<ArmorStandSize> getType() {
        return ArmorStandPropertyTypes.SIZE;
    }

    @Override
    public @NotNull ArmorStandSize getValue() {
        return entity.isSmall() ? ArmorStandSize.SMALL : ArmorStandSize.NORMAL;
    }

    @Override
    public boolean setValue(@NotNull ArmorStandSize value) {
        entity.setSmall(value.isSmall());
        return true;
    }
}
