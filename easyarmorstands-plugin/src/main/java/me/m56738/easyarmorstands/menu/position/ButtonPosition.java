package me.m56738.easyarmorstands.menu.position;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;

class ButtonPosition implements MenuSlotPosition {
    static final ButtonPosition INSTANCE = new ButtonPosition();

    @Override
    public void place(MenuBuilder builder, MenuSlot slot) {
        builder.addButton(slot);
    }
}
