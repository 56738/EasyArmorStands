package me.m56738.easyarmorstands.property.armorstand;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.EnumTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ArmorStandSizeProperty implements Property<ArmorStandSize> {
    public static final PropertyType<ArmorStandSize> TYPE = new Type();
    private final ArmorStand entity;

    public ArmorStandSizeProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ArmorStandSize> getType() {
        return TYPE;
    }

    @Override
    public ArmorStandSize getValue() {
        return entity.isSmall() ? ArmorStandSize.SMALL : ArmorStandSize.NORMAL;
    }

    @Override
    public boolean setValue(ArmorStandSize value) {
        entity.setSmall(value.isSmall());
        return true;
    }

    private static class Type implements EnumTogglePropertyType<ArmorStandSize> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.armorstand.size";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("size");
        }

        @Override
        public @NotNull Component getValueComponent(ArmorStandSize value) {
            switch (value) {
                case SMALL:
                    return Component.text("small", NamedTextColor.BLUE);
                case NORMAL:
                    return Component.text("large", NamedTextColor.GREEN);
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public ItemStack createItem(Property<ArmorStandSize> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.BONE_MEAL,
                    Component.text("Toggle size", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes the size of", NamedTextColor.GRAY),
                            Component.text("the armor stand.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
