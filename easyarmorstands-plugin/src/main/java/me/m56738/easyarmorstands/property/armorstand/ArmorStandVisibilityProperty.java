package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandVisibilityProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = EntityPropertyTypes.VISIBLE;
    private final ArmorStand entity;

    public ArmorStandVisibilityProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isVisible();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setVisible(value);
        return true;
    }
}
