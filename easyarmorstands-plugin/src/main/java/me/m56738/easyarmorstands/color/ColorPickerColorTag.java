package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Inserting;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

public class ColorPickerColorTag implements Inserting {
    private final ColorPickerContext context;

    public ColorPickerColorTag(ColorPickerContext context) {
        this.context = context;
    }

    @Override
    public @NotNull Component value() {
        return Util.formatColor(context.getColor());
    }

    @Override
    public boolean allowsChildren() {
        return false;
    }
}
