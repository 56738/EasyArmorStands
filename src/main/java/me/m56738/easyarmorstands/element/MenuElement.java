package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.command.sender.EasPlayer;

public interface MenuElement extends Element {
    void openMenu(EasPlayer player);

    boolean hasItemSlots();
}
