package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.builder.SimpleMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

import static me.m56738.easyarmorstands.menu.Menu.index;

public class ColorPicker {
    private ColorPicker() {
    }

    public static Menu create(ItemPropertySlot slot, Consumer<MenuClick> callback) {
        return create(slot.getProperty(), slot.getContainer(), callback);
    }

    public static Menu create(Property<ItemStack> property, PropertyContainer container, Consumer<MenuClick> callback) {
        SimpleMenuBuilder builder = new SimpleMenuBuilder();

        ColorPickerContext context = new ColorPickerContext(property, container);

        builder.setSlot(2, new ColorIndicatorSlot(context, callback));

        // names unstable across versions, use legacy numbers
        @SuppressWarnings("deprecation") DyeColor gray = DyeColor.getByWoolData((byte) 7);
        @SuppressWarnings("deprecation") DyeColor lightGray = DyeColor.getByWoolData((byte) 8);

        for (ColorAxis axis : ColorAxis.values()) {
            int row = axis.ordinal() + 1;
            builder.setSlot(index(row, 1), new ColorAxisChangeSlot(context, axis, gray, -10, -1, -50));
            builder.setSlot(index(row, 2), new ColorAxisSlot(context, axis));
            builder.setSlot(index(row, 3), new ColorAxisChangeSlot(context, axis, lightGray, 10, 1, 50));
        }

        int row = 0;
        int column = 5;
        for (DyeColor color : DyeColor.values()) {
            builder.setSlot(index(row, column), new ColorPresetSlot(context, color));
            column++;
            if (column >= 9) {
                row++;
                column = 5;
                if (row >= 4) {
                    break;
                }
            }
        }

        return builder.build(Component.text("Color picker"));
    }
}
