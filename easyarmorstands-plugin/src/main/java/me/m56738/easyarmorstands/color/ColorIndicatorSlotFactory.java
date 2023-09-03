package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorIndicatorSlotFactory implements MenuSlotFactory {
    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        ColorPickerContext colorPickerContext = context.colorPicker();
        if (colorPickerContext != null) {
            return new ColorIndicatorSlot(colorPickerContext);
        } else {
            return null;
        }
    }
}
