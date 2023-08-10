package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.key.Key;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandLockProperty implements BooleanToggleProperty {
    public static final Key<ArmorStandLockProperty> KEY = Key.of(ArmorStandLockProperty.class);
    private final ArmorStand entity;
    private final LockCapability lockCapability;

    public ArmorStandLockProperty(ArmorStand entity, LockCapability lockCapability) {
        this.entity = entity;
        this.lockCapability = lockCapability;
    }

    @Override
    public Boolean getValue() {
        return lockCapability.isLocked(entity);
    }

    @Override
    public void setValue(Boolean value) {
        lockCapability.setLocked(entity, value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, e -> new ArmorStandLockProperty(e, lockCapability), oldValue, value, Component.text("Changed ").append(getDisplayName()));
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
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.lock";
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.IRON_BARS,
                Component.text("Toggle lock", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("equipment can be changed", NamedTextColor.GRAY),
                        Component.text("by right clicking.", NamedTextColor.GRAY)
                )
        );
    }
}
