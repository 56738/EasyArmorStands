package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

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
    public void onClick(MenuClick click) {
        click.close();
    }
}
