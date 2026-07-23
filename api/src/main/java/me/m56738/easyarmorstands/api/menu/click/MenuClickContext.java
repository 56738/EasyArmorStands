package me.m56738.easyarmorstands.api.menu.click;

import me.m56738.easyarmorstands.platform.entity.Player;

public interface MenuClickContext {
    Player player();

    boolean isLeftClick();

    boolean isRightClick();

    boolean isShiftClick();

    void closeMenu();

    void updateMenu();
}
