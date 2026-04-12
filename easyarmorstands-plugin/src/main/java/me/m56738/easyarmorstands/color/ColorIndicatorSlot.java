package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorIndicatorSlot implements ColorSlot {
    private final ColorPickerContext context;

    public ColorIndicatorSlot(ColorPickerContext context) {
        this.context = context;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return context.item();
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        click.close();
    }
}
