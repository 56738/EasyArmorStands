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
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ColorAxisButton implements MenuButton {
    private final ColorPickerContext context;
    private final Key key;
    private final ColorAxis axis;

    public ColorAxisButton(ColorPickerContext context, Key key, ColorAxis axis) {
        this.context = context;
        this.key = key;
        this.axis = axis;
    }

    public static MenuLayoutRule rule(ColorAxis axis) {
        return button -> button instanceof ColorAxisButton colorAxisButton && colorAxisButton.axis == axis;
    }

    @Override
    public Key key() {
        return key;
    }

    @Override
    public MenuIcon icon() {
        return axis.getIcon();
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
                Util.formatColor(color));
    }

    @Override
    public void onClick(MenuClickContext context) {
    }
}
