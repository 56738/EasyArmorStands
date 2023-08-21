package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandSizeProperty implements Property<ArmorStandSize> {
    private final ArmorStand entity;

    public ArmorStandSizeProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<ArmorStandSize> getType() {
        return PropertyTypes.ARMOR_STAND_SIZE;
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
}
