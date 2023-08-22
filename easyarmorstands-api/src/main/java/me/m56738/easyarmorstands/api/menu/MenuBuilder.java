package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface MenuBuilder {
    void setSlot(int index, MenuSlot slot);

    void addButton(MenuSlot button);

    void addUtility(MenuSlot utility);

    Menu build(Component title, Locale locale);
}
