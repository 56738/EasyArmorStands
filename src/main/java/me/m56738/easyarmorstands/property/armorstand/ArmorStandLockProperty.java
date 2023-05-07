package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.property.BooleanEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandLockProperty extends BooleanEntityProperty<ArmorStand> {
    private final LockCapability lockCapability;

    public ArmorStandLockProperty(LockCapability lockCapability) {
        this.lockCapability = lockCapability;
    }

    @Override
    public Boolean getValue(ArmorStand entity) {
        return lockCapability.isLocked(entity);
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        lockCapability.setLocked(entity, value);
    }

    @Override
    public @NotNull String getName() {
        return "lock";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("equipment lock");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("locked", NamedTextColor.GREEN)
                : Component.text("unlocked", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.lock";
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        return Util.createItem(
                ItemType.IRON_BARS,
                Component.text("Toggle lock", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("equipment can be changed", NamedTextColor.GRAY),
                        Component.text("by right clicking.", NamedTextColor.GRAY)
                )
        );
    }
}
