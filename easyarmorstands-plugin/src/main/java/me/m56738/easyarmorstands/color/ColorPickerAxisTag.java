package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.Inserting;
import org.jetbrains.annotations.NotNull;

public class ColorPickerAxisTag implements Inserting {
    private final ColorPickerContext context;
    private final ColorAxis axis;

    public ColorPickerAxisTag(ColorPickerContext context, ColorAxis axis) {
        this.context = context;
        this.axis = axis;
    }

    @Override
    public @NotNull Component value() {
        return Component.text(axis.get(context.getColor()));
    }

    @Override
    public boolean allowsChildren() {
        return false;
    }
}
