package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanEntityProperty;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandBasePlateProperty extends BooleanEntityProperty<ArmorStand> {
    @Override
    public Boolean getValue(ArmorStand entity) {
        return entity.hasBasePlate();
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        entity.setBasePlate(value);
    }

    @Override
    public @NotNull String getName() {
        return "baseplate";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("base plate");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("has a base plate", NamedTextColor.GREEN)
                : Component.text("has no base plate", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.baseplate";
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        return Util.createItem(
                ItemType.STONE_SLAB,
                Component.text("Toggle base plate", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the base", NamedTextColor.GRAY),
                        Component.text("plate of the armor stand", NamedTextColor.GRAY),
                        Component.text("is visible.", NamedTextColor.GRAY)
                )
        );
    }
}
