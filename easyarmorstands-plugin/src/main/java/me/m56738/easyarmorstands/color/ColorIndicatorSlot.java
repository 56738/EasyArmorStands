package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorIndicatorSlot implements ColorSlot {
    private final ColorPickerContext context;

    public ColorIndicatorSlot(ColorPickerContext context) {
        this.context = context;
    }

    @Override
    public Item getItem(Locale locale) {
        return context.item();
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        click.close();
    }
}
