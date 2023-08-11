package me.m56738.easyarmorstands.property.v1_19_4.display.item;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.EnumTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.ItemDisplay.ItemDisplayTransform;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;

public class ItemDisplayTransformProperty implements Property<ItemDisplayTransform> {
    public static final PropertyType<ItemDisplayTransform> TYPE = new Type();
    private final ItemDisplay entity;

    public ItemDisplayTransformProperty(ItemDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<ItemDisplayTransform> getType() {
        return TYPE;
    }

    @Override
    public ItemDisplayTransform getValue() {
        return entity.getItemDisplayTransform();
    }

    @Override
    public boolean setValue(ItemDisplayTransform value) {
        entity.setItemDisplayTransform(value);
        return true;
    }

    private static class Type implements EnumTogglePropertyType<ItemDisplayTransform> {
        @Override
        public @Nullable String getPermission() {
            return "easyarmorstands.property.display.item.transform";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("item transform");
        }

        @Override
        public @NotNull Component getValueComponent(ItemDisplayTransform value) {
            return Component.text(value.name().toLowerCase(Locale.ROOT));
        }

        @Override
        public ItemStack createItem(Property<ItemDisplayTransform> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.STICK,
                    Component.text("Toggle transform mode", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes which of the", NamedTextColor.GRAY),
                            Component.text("transforms defined in", NamedTextColor.GRAY),
                            Component.text("the model is used.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
