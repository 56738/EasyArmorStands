package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.text.Component;

import java.util.List;

public interface ButtonHandler {
    void onClick(MenuClickContext context);

    default MenuIcon modifyIcon(MenuIcon icon) {
        return icon;
    }

    default void modifyDescription(List<Component> description) {
    }
}
