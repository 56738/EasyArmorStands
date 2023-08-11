package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.EnumTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAlignment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;

public class TextDisplayAlignmentProperty implements Property<TextAlignment> {
    public static final PropertyType<TextAlignment> TYPE = new Type();
    private final TextDisplay entity;

    public TextDisplayAlignmentProperty(TextDisplay entity) {
        this.entity = entity;
    }

    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getAlignment");
            TextDisplay.class.getMethod("setAlignment", TextAlignment.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public PropertyType<TextAlignment> getType() {
        return TYPE;
    }

    @Override
    public TextAlignment getValue() {
        return entity.getAlignment();
    }

    @Override
    public boolean setValue(TextAlignment value) {
        entity.setAlignment(value);
        return true;
    }

    private static class Type implements EnumTogglePropertyType<TextAlignment> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text.alignment";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("alignment");
        }

        @Override
        public Component getValueComponent(TextAlignment value) {
            return Component.text(value.name().toLowerCase(Locale.ROOT));
        }

        @Override
        public ItemStack createItem(Property<TextAlignment> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.FEATHER,
                    Component.text("Toggle alignment", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes the text", NamedTextColor.GRAY),
                            Component.text("alignment.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
