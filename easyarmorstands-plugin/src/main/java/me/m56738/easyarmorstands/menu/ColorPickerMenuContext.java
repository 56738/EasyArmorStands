package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ColorPickerMenuContext extends SimpleMenuContext {
    private final ColorPickerContext context;

    public ColorPickerMenuContext(Player player, ColorPickerContext context) {
        super(player);
        this.context = context;
    }

    @Override
    public @Nullable ColorPickerContext colorPicker() {
        return context;
    }
}
