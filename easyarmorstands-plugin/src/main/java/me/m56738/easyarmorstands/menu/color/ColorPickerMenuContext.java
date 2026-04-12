package me.m56738.easyarmorstands.menu.color;

import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.SimpleMenuContext;
import org.jetbrains.annotations.Nullable;

public class ColorPickerMenuContext extends SimpleMenuContext {
    private final ColorPickerContext context;

    public ColorPickerMenuContext(EasPlayer player, ColorPickerContext context) {
        super(player);
        this.context = context;
    }

    @Override
    public @Nullable ColorPickerContext colorPicker() {
        return context;
    }
}
