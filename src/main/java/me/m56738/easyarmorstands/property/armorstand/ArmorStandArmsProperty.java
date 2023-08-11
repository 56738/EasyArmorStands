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

public class ArmorStandArmsProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final ArmorStand entity;

    public ArmorStandArmsProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.hasArms();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setArms(value);
        return true;
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.armorstand.arms";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("arms");
        }

        @Override
        public @NotNull Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("has arms", NamedTextColor.GREEN)
                    : Component.text("has no arms", NamedTextColor.RED);
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.STICK,
                    Component.text("Toggle arms", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the arms", NamedTextColor.GRAY),
                            Component.text("of the armor stand are", NamedTextColor.GRAY),
                            Component.text("visible.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
