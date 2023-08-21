package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCanTickProperty implements Property<Boolean> {
    private final ArmorStand entity;
    private final TickCapability tickCapability;

    public ArmorStandCanTickProperty(ArmorStand entity, TickCapability tickCapability) {
        this.entity = entity;
        this.tickCapability = tickCapability;
    }

    public static boolean isSupported() {
        return EasyArmorStands.getInstance().getCapability(TickCapability.class) != null;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return PropertyTypes.ARMOR_STAND_CAN_TICK;
    }

    @Override
    public Boolean getValue() {
        return tickCapability.canTick(entity);
    }

    @Override
    public boolean setValue(Boolean value) {
        tickCapability.setCanTick(entity, value);
        return true;
    }
}
