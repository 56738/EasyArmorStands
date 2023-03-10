package me.m56738.easyarmorstands.color;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;

public class ColorAxisChangeSlot extends ColorAxisSlot {
    private final ColorPicker menu;
    private final ColorAxis axis;
    private final DyeColor displayColor;
    private final int change;

    public ColorAxisChangeSlot(ColorPicker menu, ColorAxis axis, DyeColor displayColor, int change) {
        super(menu, axis);
        this.menu = menu;
        this.axis = axis;
        this.displayColor = displayColor;
        this.change = change;
    }

    @Override
    protected String getName() {
        if (change >= 0) {
            return super.getName() + " + " + change;
        } else {
            return super.getName() + " - " + -change;
        }
    }

    @Override
    public DyeColor getDisplayColor() {
        return displayColor;
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ItemStack cursor) {
        if (click) {
            Color color = menu.getColor();
            int value = axis.get(color);
            int newValue = Math.max(0, Math.min(255, value + change));
            Color newColor = axis.set(color, newValue);
            menu.setColor(newColor);
        }
        return false;
    }
}
