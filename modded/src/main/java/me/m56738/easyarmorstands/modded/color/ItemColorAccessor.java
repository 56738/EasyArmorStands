package me.m56738.easyarmorstands.modded.color;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import net.minecraft.world.item.ItemStack;

public interface ItemColorAccessor {
    boolean isSupported(ItemStack item);

    RGBColor getColor(ItemStack item);

    void setColor(ItemStack item, RGBColor color);
}
