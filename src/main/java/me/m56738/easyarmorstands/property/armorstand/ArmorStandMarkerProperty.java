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

public class ArmorStandMarkerProperty extends BooleanEntityProperty<ArmorStand> {
    @Override
    public Boolean getValue(ArmorStand entity) {
        return entity.isMarker();
    }

    @Override
    public void setValue(ArmorStand entity, Boolean value) {
        entity.setMarker(value);
    }

    @Override
    public @NotNull String getName() {
        return "marker";
    }

    @Override
    public @NotNull Class<ArmorStand> getEntityType() {
        return ArmorStand.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("marker");
    }

    @Override
    public @NotNull Component getValueName(Boolean value) {
        return value
                ? Component.text("a marker", NamedTextColor.GREEN)
                : Component.text("not a marker", NamedTextColor.RED);
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.marker";
    }

    @Override
    public ItemStack createToggleButton(ArmorStand entity) {
        return Util.createItem(
                ItemType.SUNFLOWER,
                Component.text("Toggle marker", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Currently ", NamedTextColor.GRAY)
                                .append(getValueName(getValue(entity)))
                                .append(Component.text(".")),
                        Component.text("Changes whether the", NamedTextColor.GRAY),
                        Component.text("armor stand is a marker", NamedTextColor.GRAY),
                        Component.text("with zero size and without", NamedTextColor.GRAY),
                        Component.text("collision or interaction.", NamedTextColor.GRAY)
                )
        );
    }
}
