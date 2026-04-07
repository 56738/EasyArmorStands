package me.m56738.easyarmorstands.api.menu.button;

import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public interface MenuButton {
    Material icon();

    Component name();

    List<Component> description();

    default MenuButtonCategory category() {
        return MenuButtonCategory.DEFAULT;
    }

    void onClick(MenuClickContext context);
}
