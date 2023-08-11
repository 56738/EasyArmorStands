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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArmorStandGravityProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final ArmorStand entity;

    public ArmorStandGravityProperty(ArmorStand entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.hasGravity();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setGravity(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.gravity";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("gravity");
        }

        @Override
        public @NotNull Component getValueComponent(Boolean value) {
            return value
                    ? Component.text("enabled", NamedTextColor.GREEN)
                    : Component.text("static", NamedTextColor.RED);
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            Property<Boolean> canTickProperty = container.getOrNull(ArmorStandCanTickProperty.TYPE);
            List<Component> description = Arrays.asList(
                    Component.text("Currently ", NamedTextColor.GRAY)
                            .append(canTickProperty != null && !canTickProperty.getValue()
                                    ? Component.text("frozen", NamedTextColor.GOLD)
                                    : property.getValue()
                                    ? Component.text("has gravity", NamedTextColor.GREEN)
                                    : Component.text("static", NamedTextColor.RED))
                            .append(Component.text(".")),
                    Component.text("Changes whether the", NamedTextColor.GRAY),
                    Component.text("armor stand will fall", NamedTextColor.GRAY),
                    Component.text("due to gravity.", NamedTextColor.GRAY)
            );
            if (canTickProperty != null && property.getValue() && !canTickProperty.getValue()) {
                description = new ArrayList<>(description);
                description.add(Component.text("Gravity is enabled but", NamedTextColor.GOLD));
                description.add(Component.text("armor stand ticking is", NamedTextColor.GOLD));
                description.add(Component.text("disabled, so gravity", NamedTextColor.GOLD));
                description.add(Component.text("has no effect.", NamedTextColor.GOLD));
            }
            return Util.createItem(
                    ItemType.FEATHER,
                    Component.text("Toggle gravity", NamedTextColor.BLUE),
                    description
            );
        }

        @Override
        public void onClick(Property<Boolean> property, PropertyContainer container) {
            BooleanTogglePropertyType.super.onClick(property, container);

            // Attempt to also turn on armor stand ticking when turning on gravity
            Property<Boolean> canTickProperty = container.getOrNull(ArmorStandCanTickProperty.TYPE);
            if (property.getValue() && canTickProperty != null && !canTickProperty.getValue()) {
                canTickProperty.setValue(true);
            }
        }
    }
}
