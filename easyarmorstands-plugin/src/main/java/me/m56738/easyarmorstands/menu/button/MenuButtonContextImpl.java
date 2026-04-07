package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.api.menu.button.MenuButtonContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;

import java.util.Locale;

public record MenuButtonContextImpl(Locale locale) implements MenuButtonContext {
    @Override
    public Component render(Component component) {
        return GlobalTranslator.render(component, locale);
    }
}
