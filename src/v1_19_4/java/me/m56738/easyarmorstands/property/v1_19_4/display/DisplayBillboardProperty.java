package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.property.TogglePropertyType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;

public class DisplayBillboardProperty implements Property<Billboard> {
    public static final PropertyType<Billboard> TYPE = new Type();
    private final Display entity;

    public DisplayBillboardProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Billboard> getType() {
        return TYPE;
    }

    @Override
    public Billboard getValue() {
        return entity.getBillboard();
    }

    @Override
    public boolean setValue(Billboard value) {
        entity.setBillboard(value);
        return true;
    }

    private static class Type implements TogglePropertyType<Billboard> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.billboard";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("billboard");
        }

        @Override
        public Component getValueComponent(Billboard value) {
            return Component.text(value.name().toLowerCase(Locale.ROOT),
                    value == Billboard.FIXED ? NamedTextColor.RED : NamedTextColor.GREEN);
        }

        @Override
        public Billboard getNextValue(Billboard value) {
            Billboard[] values = Billboard.values();
            return values[(value.ordinal() + 1) % values.length];
        }

        @Override
        public ItemStack createItem(Property<Billboard> property, PropertyContainer container) {
            return Util.createItem(
                    ItemType.IRON_BARS,
                    Component.text("Toggle billboard", NamedTextColor.BLUE),
                    Arrays.asList(
                            Component.text("Currently ", NamedTextColor.GRAY)
                                    .append(getValueComponent(property.getValue()))
                                    .append(Component.text(".")),
                            Component.text("Changes whether the", NamedTextColor.GRAY),
                            Component.text("entity rotates with", NamedTextColor.GRAY),
                            Component.text("the player camera.", NamedTextColor.GRAY)
                    )
            );
        }
    }
}
