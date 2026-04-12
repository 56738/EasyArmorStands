package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

public class ColorPickerContextImpl implements ColorPickerContext {
    private final Property<ItemStack> property;

    public ColorPickerContextImpl(Property<ItemStack> property) {
        this.property = property;
    }

    @Override
    public @NotNull ItemStack item() {
        return property.getValue();
    }

    @Override
    public @NotNull Color getColor() {
        ItemStack item = property.getValue();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return Color.WHITE;
        }
        return getColor(meta);
    }

    @Override
    public void setColor(@NotNull Color color) {
        ItemStack item = property.getValue().clone();
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        if (setColor(meta, color)) {
            item.setItemMeta(meta);
            property.setValue(item);
            property.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
        }
    }

    public static boolean hasColor(ItemMeta meta) {
        return meta instanceof LeatherArmorMeta || meta instanceof MapMeta;
    }

    public static Color getColor(ItemMeta meta) {
        if (meta instanceof LeatherArmorMeta) {
            return ((LeatherArmorMeta) meta).getColor();
        }
        if (meta instanceof MapMeta) {
            MapMeta mapMeta = (MapMeta) meta;
            if (!mapMeta.hasColor()) {
                return Color.fromRGB(0x46402E);
            }
            return mapMeta.getColor();
        }
        return null;
    }

    public static boolean setColor(ItemMeta meta, Color color) {
        if (meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) meta).setColor(color);
            return true;
        }
        if (meta instanceof MapMeta) {
            ((MapMeta) meta).setColor(color);
            return true;
        }
        return false;
    }
}
