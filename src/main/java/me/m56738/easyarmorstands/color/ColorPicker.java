package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlot;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;

public class ColorPicker extends Menu {
    private final Runnable callback;

    private ColorPicker(Component title, MenuSlot[] slots, Locale locale, Runnable callback) {
        super(title, slots, locale);
        this.callback = callback;
    }

    public static Menu create(ItemPropertySlot slot, Locale locale, Runnable callback) {
        return create(slot.getProperty(), slot.getContainer(), locale, callback);
    }

    public static Menu create(Property<ItemStack> property, PropertyContainer container, Locale locale, Runnable callback) {
        MenuSlot[] slots = new MenuSlot[9 * 4];
        Arrays.fill(slots, BackgroundSlot.INSTANCE);

        ColorPickerContext context = new ColorPickerContext(property, container);

        slots[2] = new ColorIndicatorSlot(context);

        // names unstable across versions, use legacy numbers
        @SuppressWarnings("deprecation") DyeColor gray = DyeColor.getByWoolData((byte) 7);
        @SuppressWarnings("deprecation") DyeColor lightGray = DyeColor.getByWoolData((byte) 8);

        for (ColorAxis axis : ColorAxis.values()) {
            int row = axis.ordinal() + 1;
            slots[index(row, 1)] = new ColorAxisChangeSlot(context, axis, gray, -10, -1, -50);
            slots[index(row, 2)] = new ColorAxisSlot(context, axis);
            slots[index(row, 3)] = new ColorAxisChangeSlot(context, axis, lightGray, 10, 1, 50);
        }

        int row = 0;
        int column = 5;
        for (DyeColor color : DyeColor.values()) {
            slots[index(row, column)] = new ColorPresetSlot(context, color);
            column++;
            if (column >= 9) {
                row++;
                column = 5;
                if (row >= 4) {
                    break;
                }
            }
        }

        Component title = MiniMessage.miniMessage().deserialize(EasyArmorStands.getInstance().getConfig().getString("menu.color-picker.title"));
        return new ColorPicker(title, slots, locale, callback);
    }

    @Override
    public void close(Player player) {
        if (callback != null) {
            callback.run();
        } else {
            super.close(player);
        }
    }
}
