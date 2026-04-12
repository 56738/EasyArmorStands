package me.m56738.easyarmorstands.menu.factory;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuContext;

public interface MenuFactory {
    Menu createMenu(MenuContext context);
}
