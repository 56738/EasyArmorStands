package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
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
