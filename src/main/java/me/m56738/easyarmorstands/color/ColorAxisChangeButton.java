package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ColorAxisChangeButton implements MenuButton {
    private final ColorPickerContext context;
    private final Key key;
    private final MenuIcon icon;
    private final ColorAxis axis;
    private final int leftChange;
    private final int rightChange;
    private final int shiftChange;

    public ColorAxisChangeButton(ColorPickerContext context, Key key, MenuIcon icon, ColorAxis axis, int leftChange, int rightChange, int shiftChange) {
        this.context = context;
        this.key = key;
        this.icon = icon;
        this.axis = axis;
        this.leftChange = leftChange;
        this.rightChange = rightChange;
        this.shiftChange = shiftChange;
    }

    public static MenuLayoutRule rule(ColorAxis axis, boolean positive) {
        return button -> button instanceof ColorAxisChangeButton colorAxisChangeButton
                && colorAxisChangeButton.axis == axis
                && (colorAxisChangeButton.leftChange > 0) == positive;
    }

    @Override
    public Key key() {
        return key;
    }

    @Override
    public MenuIcon icon() {
        return icon;
    }

    @Override
    public Component name() {
        return axis.getDisplayName();
    }

    @Override
    public List<Component> description() {
        Color color = context.getColor();
        int value = axis.get(color);
        return List.of(
                Component.text(value),
                Util.formatColor(color),
                Component.translatable("easyarmorstands.menu.color-picker.change"),
                Component.translatable("easyarmorstands.menu.color-picker.change.right-click"),
                Component.translatable("easyarmorstands.menu.color-picker.change.shift-click"));
    }

    @Override
    public void onClick(MenuClickContext context) {
        int change;
        if (context.isShiftClick()) {
            change = shiftChange;
        } else if (context.isRightClick()) {
            change = rightChange;
        } else if (context.isLeftClick()) {
            change = leftChange;
        } else {
            return;
        }
        Color color = this.context.getColor();
        int value = axis.get(color);
        int newValue = Math.clamp(value + change, 0, 255);
        Color newColor = axis.set(color, newValue);
        this.context.setColor(newColor);
        context.updateMenu();
    }
}
