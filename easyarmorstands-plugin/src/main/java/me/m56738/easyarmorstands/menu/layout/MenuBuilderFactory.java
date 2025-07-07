package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

import java.util.Locale;

@FunctionalInterface
@NullMarked
public interface MenuBuilderFactory {
    MenuBuilder createMenuBuilder(Component title, Locale locale, MenuSlot background);
}
