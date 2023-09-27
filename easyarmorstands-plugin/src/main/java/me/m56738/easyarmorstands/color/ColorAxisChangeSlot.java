package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

public class ColorAxisChangeSlot extends ColorAxisSlot {
    private final ColorPickerContext context;
    private final ColorAxis axis;
    private final int leftChange;
    private final int rightChange;
    private final int shiftChange;

    public ColorAxisChangeSlot(ColorPickerContext context, ColorAxis axis, ItemTemplate itemTemplate, TagResolver resolver, int leftChange, int rightChange, int shiftChange) {
        super(context, axis, itemTemplate, resolver);
        this.context = context;
        this.axis = axis;
        this.leftChange = leftChange;
        this.rightChange = rightChange;
        this.shiftChange = shiftChange;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        int change;
        if (click.isShiftClick()) {
            change = shiftChange;
        } else if (click.isRightClick()) {
            change = rightChange;
        } else if (click.isLeftClick()) {
            change = leftChange;
        } else {
            return;
        }
        Color color = context.getColor();
        int value = axis.get(color);
        int newValue = Math.max(0, Math.min(255, value + change));
        Color newColor = axis.set(color, newValue);
        context.setColor(newColor);
    }
}
