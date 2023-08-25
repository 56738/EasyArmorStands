package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
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
        return EasyArmorStandsPlugin.getInstance().getCapability(TickCapability.class) != null;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return ArmorStandPropertyTypes.CAN_TICK;
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
