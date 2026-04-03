package me.m56738.easyarmorstands.api.menu;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ColorPickerContext {
    @NotNull ItemStack item();

    @NotNull Color getColor();

    void setColor(@NotNull Color color);
}
