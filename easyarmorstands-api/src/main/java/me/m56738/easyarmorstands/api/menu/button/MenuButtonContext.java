package me.m56738.easyarmorstands.api.menu.button;

import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface MenuButtonContext {
    Locale locale();

    Component render(Component component);
}
