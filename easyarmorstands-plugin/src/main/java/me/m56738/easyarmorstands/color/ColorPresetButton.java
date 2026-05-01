package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ColorPresetButton implements MenuButton {
    private final ColorPickerContext context;
    private final Key key;
    private final MenuIcon icon;
    private final Component name;
    private final Color color;

    public ColorPresetButton(ColorPickerContext context, Key key, MenuIcon icon, Component name, Color color) {
        this.context = context;
        this.key = key;
        this.icon = icon;
        this.name = name;
        this.color = color;
    }

    public static MenuLayoutRule rule() {
        return button -> button instanceof ColorPresetButton;
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
        return name;
    }

    @Override
    public List<Component> description() {
        return List.of(
                Component.translatable("easyarmorstands.menu.color-picker.left-click-to-select"),
                Component.translatable("easyarmorstands.menu.color-picker.right-click-to-mix")
        );
    }

    @Override
    public void onClick(MenuClickContext context) {
        if (context.isLeftClick()) {
            this.context.setColor(color);
        } else if (context.isRightClick()) {
            this.context.setColor(this.context.getColor().mixColors(color));
        }
        context.updateMenu();
    }
}
