package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ColorIndicatorSlot implements ColorSlot {
    private final ColorPickerContext context;
    private final Consumer<MenuClick> callback;

    public ColorIndicatorSlot(ColorPickerContext context, Consumer<MenuClick> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public ItemStack getItem() {
        return context.getItem();
    }

    @Override
    public void onClick(MenuClick click) {
        if (callback != null) {
            callback.accept(click);
        }
    }
}
