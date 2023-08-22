package me.m56738.easyarmorstands.menu.position;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;

class UtilityPosition implements MenuSlotPosition {
    static final UtilityPosition INSTANCE = new UtilityPosition();

    @Override
    public void place(MenuBuilder builder, MenuSlot slot) {
        builder.addUtility(slot);
    }
}
