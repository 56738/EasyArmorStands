package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.util.Color;
import org.jetbrains.annotations.NotNull;

public interface ColorPickerContext {
    @NotNull Item item();

    @NotNull Color getColor();

    void setColor(@NotNull Color color);
}
