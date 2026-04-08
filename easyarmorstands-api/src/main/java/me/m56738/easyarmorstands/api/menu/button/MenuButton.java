package me.m56738.easyarmorstands.api.menu.button;

import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;

import java.util.List;

public interface MenuButton extends Keyed {
    MenuIcon icon();

    Component name();

    List<Component> description();

    default MenuButtonCategory category() {
        return MenuButtonCategory.DEFAULT;
    }

    void onClick(MenuClickContext context);
}
