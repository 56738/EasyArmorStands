package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuBuilderFactory;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public class DialogMenuBuilderFactory implements MenuBuilderFactory {
    @Override
    public MenuBuilder createBuilder(Component title, Locale locale) {
        return new DialogMenuBuilder(title, locale);
    }
}
