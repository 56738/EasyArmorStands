package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface MenuBuilder {
    void addButton(MenuSlot button);

    void addUtility(MenuSlot utility);

    Menu build(Component title, Locale locale);
}
