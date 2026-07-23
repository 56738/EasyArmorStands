package me.m56738.easyarmorstands.paper.color;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.jspecify.annotations.Nullable;

public class ColorPickerContextImpl implements ColorPickerContext {
    private final Platform platform;
    private final Property<ItemStack> property;

    public ColorPickerContextImpl(Platform platform, Property<ItemStack> property) {
        this.platform = platform;
        this.property = property;
    }

    public static boolean hasColor(@Nullable ItemMeta meta) {
        return meta instanceof LeatherArmorMeta || meta instanceof MapMeta;
    }

    public static @Nullable Color getColor(@Nullable ItemMeta meta) {
        if (meta instanceof LeatherArmorMeta) {
            return ((LeatherArmorMeta) meta).getColor();
        }
        if (meta instanceof MapMeta mapMeta) {
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

    @Override
    public Platform platform() {
        return platform;
    }

    @Override
    public ItemStack item() {
        return property.getValue();
    }

    @Override
    public RGBColor getColor() {
        ItemMeta meta = PaperItemStack.toNative(property.getValue()).getItemMeta();
        Color color = getColor(meta);
        if (color == null) {
            return RGBColor.WHITE;
        }
        return RGBColor.of(color.asRGB());
    }

    @Override
    public void setColor(RGBColor color) {
        org.bukkit.inventory.ItemStack item = PaperItemStack.toNative(property.getValue());
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        if (setColor(meta, Color.fromRGB(color.value()))) {
            item.setItemMeta(meta);
            property.setValue(PaperItemStack.fromNative(item));
            property.commit(Message.component("easyarmorstands.history.changed-color", Util.formatColor(color)));
        }
    }
}
