package me.m56738.easyarmorstands.menu.color;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ColorPickerContext {
    @NotNull Platform platform();

    @NotNull ItemStack item();

    @NotNull RGBColor getColor();

    void setColor(@NotNull RGBColor color);
}
