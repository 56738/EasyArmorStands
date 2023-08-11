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

public class ArmorStandVisibilityProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final ArmorStand entity;

    public ArmorStandVisibilityProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isVisible();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setVisible(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.visible";
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
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.INVISIBILITY_POTION,
                    Component.text("Toggle visibility", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the", NamedTextColor.GRAY),
                            Component.text("armor stand is visible.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
