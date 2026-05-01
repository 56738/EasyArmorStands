package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.color.ColorPickerContext;
import me.m56738.easyarmorstands.menu.layout.MenuLayoutRule;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class ColorIndicatorButton implements MenuButton {
    public static final Key KEY = EasyArmorStands.key("color_picker/indicator");

    private final ColorPickerContext context;

    public ColorIndicatorButton(ColorPickerContext context) {
        this.context = context;
    }

    public static MenuLayoutRule rule() {
        return button -> button instanceof ColorIndicatorButton;
    }

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public MenuIcon icon() {
        return MenuIcon.of(context.item());
    }

    @Override
    public Component name() {
        return Component.empty();
    }

    @Override
    public List<Component> description() {
        return List.of();
    }

    @Override
    public void onClick(MenuClickContext context) {
        context.closeMenu();
    }
}
