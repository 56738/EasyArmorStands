package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCanTickProperty implements Property<Boolean> {
    private final ArmorStand entity;

    public ArmorStandCanTickProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.CAN_TICK;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.canTick();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setCanTick(value);
        return true;
    }
}
