package me.m56738.easyarmorstands.api.menu.click;

import org.bukkit.entity.Player;

public interface MenuClickContext {
    Player player();

    boolean isLeftClick();

    boolean isRightClick();

    boolean isShiftClick();

    void closeMenu();

    void updateMenu();
}
