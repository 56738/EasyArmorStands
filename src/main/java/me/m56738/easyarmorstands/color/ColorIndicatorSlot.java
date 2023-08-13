package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

public class ColorIndicatorSlot implements ColorSlot {
    private final ColorPickerContext context;

    public ColorIndicatorSlot(ColorPickerContext context) {
        this.context = context;
    }

    @Override
    public ItemStack getItem() {
        return context.getItem();
    }

    @Override
    public void onClick(MenuClick click) {
        click.close();
    }
}
