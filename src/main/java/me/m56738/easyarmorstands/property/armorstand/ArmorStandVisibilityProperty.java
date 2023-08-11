package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import me.m56738.easyarmorstands.property.BooleanToggleProperty;
import me.m56738.easyarmorstands.property.key.PropertyKey;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandVisibilityProperty implements BooleanToggleProperty {
    public static final PropertyKey<Boolean> KEY = PropertyKey.of(EasyArmorStands.key("armor_stand_visible"));
    private final ArmorStand entity;

    public ArmorStandVisibilityProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public Boolean getValue() {
        return entity.isVisible();
    }

    @Override
    public void setValue(Boolean value) {
        entity.setVisible(value);
    }

    @Override
    public Action createChangeAction(Boolean oldValue, Boolean value) {
        return new EntityPropertyAction<>(entity, ArmorStandVisibilityProperty::new, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("visibility");
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        return value
                ? Component.text("visible", NamedTextColor.GREEN)
                : Component.text("invisible", NamedTextColor.RED);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.visible";
    }

    @Override
    public ItemStack createItem() {
        return Util.createItem(
                ItemType.INVISIBILITY_POTION,
                Component.text("Toggle visibility", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueComponent(getValue()))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand is visible.", NamedTextColor.GRAY)
                )
        );
    }
}
