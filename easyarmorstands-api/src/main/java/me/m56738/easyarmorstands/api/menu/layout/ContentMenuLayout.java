package me.m56738.easyarmorstands.api.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;

/**
 * A menu layout with a single primary content slot on the left and other buttons on the right.
 */
public interface ContentMenuLayout extends MenuLayout {
    void setContentSlot(MenuSlot slot);
}
