package me.m56738.easyarmorstands.menu.factory;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuContext;

public interface MenuFactory {
    Menu createMenu(MenuContext context);
}
