package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.TreeMap;

@NullMarked
public class MenuButtonCollector implements MenuBuilder {
    private final TreeMap<Key, MenuButton> buttons = new TreeMap<>();

    public void addButton(MenuButton button) {
        buttons.put(button.key(), button);
    }

    public Collection<MenuButton> getButtons() {
        return buttons.values();
    }
}
