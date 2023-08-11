package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
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

public class ArmorStandMarkerProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final ArmorStand entity;

    public ArmorStandMarkerProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isMarker();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setMarker(value);
        return true;
    }

    private static class Type implements BooleanTogglePropertyType {

        @Override
        public String getPermission() {
            return "easyarmorstands.property.armorstand.marker";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("marker");
        }

        @Override
        public @NotNull Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("a marker", NamedTextColor.GREEN)
                    : Component.text("not a marker", NamedTextColor.RED);
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.SUNFLOWER,
                    Component.text("Toggle marker", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the", NamedTextColor.GRAY),
                            Component.text("armor stand is a marker", NamedTextColor.GRAY),
                            Component.text("with zero size and without", NamedTextColor.GRAY),
                            Component.text("collision or interaction.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
