package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.BooleanTogglePropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TextDisplayShadowProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final TextDisplay entity;

    public TextDisplayShadowProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isShadowed();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setShadowed(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text.shadow";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("text shadow");
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.STONE,
                    Component.text("Toggle shadow", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the text", NamedTextColor.GRAY),
                            Component.text("is rendered with a shadow.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
