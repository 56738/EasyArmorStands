package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.menu.slot.MenuSlotContext;
import me.m56738.easyarmorstands.menu.slot.MenuSlotFactory;
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
