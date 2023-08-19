package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.property.BooleanPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCanTickProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
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
    public PropertyType<Boolean> getType() {
        return TYPE;
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

    private static class Type implements BooleanPropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.armorstand.cantick";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("ticking");
        }
    }
}
