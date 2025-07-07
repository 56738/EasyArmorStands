package me.m56738.easyarmorstands.api.menu.layout;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuSlot;

public interface MenuLayout {
    void addSlot(MenuSlot slot);

    Menu createMenu();
}
