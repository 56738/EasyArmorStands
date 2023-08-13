package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.MenuClick;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ColorAxisChangeSlot extends ColorAxisSlot {
    private static final NumberFormat FORMAT = new DecimalFormat("+0;-0");
    private final ColorPickerContext context;
    private final ColorAxis axis;
    private final DyeColor displayColor;
    private final int leftChange;
    private final int rightChange;
    private final int shiftChange;

    public ColorAxisChangeSlot(ColorPickerContext context, ColorAxis axis, DyeColor displayColor, int leftChange, int rightChange, int shiftChange) {
        super(context, axis);
        this.context = context;
        this.axis = axis;
        this.displayColor = displayColor;
        this.leftChange = leftChange;
        this.rightChange = rightChange;
        this.shiftChange = shiftChange;
    }

    @Override
    protected DyeColor getItemColor() {
        return displayColor;
    }

    @Override
    protected List<Component> getDescription() {
        List<Component> description = new ArrayList<>(super.getDescription());
        description.add(formatChange("Left click", leftChange));
        description.add(formatChange("Right click", rightChange));
        description.add(formatChange("Shift click", shiftChange));
        return description;
    }

    private Component formatChange(String type, int change) {
        return Component.text()
                .content(type + ": ")
                .append(Component.text(FORMAT.format(change), NamedTextColor.GOLD))
                .color(NamedTextColor.GRAY)
                .build();
    }

    @Override
    public void onClick(MenuClick click) {
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
        if (color != null) {
            int value = axis.get(color);
            int newValue = Math.max(0, Math.min(255, value + change));
            Color newColor = axis.set(color, newValue);
            context.setColor(newColor, click.menu());
        }
    }
}
