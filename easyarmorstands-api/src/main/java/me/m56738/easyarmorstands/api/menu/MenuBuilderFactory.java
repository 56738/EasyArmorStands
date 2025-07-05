package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.text.Component;

import java.util.Locale;

public interface MenuBuilderFactory {
    MenuBuilder createBuilder(Component title, Locale locale);
}
