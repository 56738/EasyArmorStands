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

public class ArmorStandArmsProperty extends BooleanEntityProperty<ArmorStand> {
    @Override
    public Boolean getValue(ArmorStand entity) {
        return entity.hasArms();
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        entity.setArms(value);
    }

    @Override
    public @NotNull String getName() {
        return "arms";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("arms");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("has arms", NamedTextColor.GREEN)
                : Component.text("has no arms", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.armorstand.arms";
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        return Util.createItem(
                ItemType.STICK,
                Component.text("Toggle arms", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the arms", NamedTextColor.GRAY),
                        Component.text("of the armor stand are", NamedTextColor.GRAY),
                        Component.text("visible.", NamedTextColor.GRAY)
                )
        );
    }
}
