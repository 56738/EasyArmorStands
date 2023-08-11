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

import java.util.Arrays;

public class TextDisplaySeeThroughProperty implements Property<Boolean> {
    public static final PropertyType<Boolean> TYPE = new Type();
    private final TextDisplay entity;

    public TextDisplaySeeThroughProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TYPE;
    }

    @Override
    public Boolean getValue() {
        return entity.isSeeThrough();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setSeeThrough(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements BooleanTogglePropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text.seethrough";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("see through");
        }

        @Override
        public ItemStack createItem(Property<Boolean> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.GLOWSTONE_DUST,
                    Component.text("Toggle see through", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the text", NamedTextColor.GRAY),
                            Component.text("is visible through walls.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
