package me.m56738.easyarmorstands.common.menu;

import me.m56738.easyarmorstands.api.menu.Menu;
import net.kyori.adventure.text.Component;

public class MenuImpl implements Menu {
    private final Component title;

    public MenuImpl(Component title) {
        this.title = title;
    }

    @Override
    public Component getTitle() {
        return title;
    }
}
