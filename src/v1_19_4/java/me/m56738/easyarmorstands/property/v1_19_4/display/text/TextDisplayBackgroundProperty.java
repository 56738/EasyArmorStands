package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.property.TogglePropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@SuppressWarnings("deprecation") // presence checked in isSupported
public class TextDisplayBackgroundProperty implements Property<@Nullable Color> {
    public static final PropertyType<@Nullable Color> TYPE = new Type();
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getBackgroundColor");
            TextDisplay.class.getMethod("setBackgroundColor", Color.class);
            TextDisplay.class.getMethod("isDefaultBackground");
            TextDisplay.class.getMethod("setDefaultBackground", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public PropertyType<@Nullable Color> getType() {
        return TYPE;
    }

    @Override
    public @Nullable Color getValue() {
        if (entity.isDefaultBackground()) {
            return null;
        }
        Color color = entity.getBackgroundColor();
        if (color == null) {
            color = Color.WHITE;
        }
        return color;
    }

    @Override
    public boolean setValue(@Nullable Color value) {
        if (value != null) {
            entity.setDefaultBackground(false);
            entity.setBackgroundColor(value);
        } else {
            entity.setDefaultBackground(true);
            entity.setBackgroundColor(null);
        }
        return true;
    }

    private static class Type implements TogglePropertyType<@Nullable Color> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text.background";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("background");
        }

        @Override
        public Component getValueComponent(@Nullable Color value) {
            if (value != null) {
                if (value.getAlpha() == 0) {
                    return Component.text("none", NamedTextColor.WHITE);
                }
                TextColor textColor = TextColor.color(value.asRGB());
                TextComponent hex = Component.text(textColor.asHexString(), textColor);
                if (textColor instanceof NamedTextColor) {
                    return hex.append(Component.text(" (" + textColor + ")"));
                }
                return hex;
            } else {
                return Component.text("default", NamedTextColor.DARK_GRAY);
            }
        }

        @Override
        public Color getNextValue(@Nullable Color value) {
            if (value == null) {
                return Color.fromARGB(0, 0, 0, 0);
            } else {
                return null;
            }
        }

        @Override
        public @Nullable Color getPreviousValue(@Nullable Color value) {
            return getNextValue(value);
        }

        @Override
        public ItemStack createItem(Property<@Nullable Color> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.STONE_SLAB,
                    Component.text("Toggle background", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes the background", NamedTextColor.GRAY),
                            Component.text("behind the text.", NamedTextColor.GRAY),
                            Component.text("Use ", NamedTextColor.GRAY)
                                    .append(Component.text("/eas text background", NamedTextColor.BLUE)),
                            Component.text("to set the color.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
