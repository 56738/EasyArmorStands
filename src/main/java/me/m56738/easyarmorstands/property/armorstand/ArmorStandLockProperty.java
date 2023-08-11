package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.property.BooleanTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandLockProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final ArmorStand entity;
    private final LockCapability lockCapability;

    public ArmorStandLockProperty(ArmorStand entity, LockCapability lockCapability) {
        this.entity = entity;
        this.lockCapability = lockCapability;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return lockCapability.isLocked(entity);
    }

    @Override
    public boolean setValue(Boolean value) {
        lockCapability.setLocked(entity, value);
        return true;
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.armorstand.lock";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("equipment lock");
        }

        @Override
        public @NotNull Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("locked", NamedTextColor.GREEN)
                    : Component.text("unlocked", NamedTextColor.RED);
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.IRON_BARS,
                    Component.text("Toggle lock", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the", NamedTextColor.GRAY),
                            Component.text("equipment can be changed", NamedTextColor.GRAY),
                            Component.text("by right clicking.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
