package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ColorAxisChangeSlot extends ColorAxisSlot {
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
    protected List<Component> getDescription(Locale locale) {
        List<Component> description = new ArrayList<>(super.getDescription(locale));
        description.add(GlobalTranslator.render(Message.component("easyarmorstands.menu.color-picker.change").color(NamedTextColor.GRAY), locale));
        description.add(GlobalTranslator.render(Message.component("easyarmorstands.menu.color-picker.change.right-click").color(NamedTextColor.GRAY), locale));
        description.add(GlobalTranslator.render(Message.component("easyarmorstands.menu.color-picker.change.shift-click").color(NamedTextColor.GRAY), locale));
        return description;
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
