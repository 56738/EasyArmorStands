package me.m56738.easyarmorstands.menu.position;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;

class IndexPosition implements MenuSlotPosition {
    private final int index;

    IndexPosition(int index) {
        this.index = index;
    }

    IndexPosition(int row, int column) {
        this(Menu.index(row, column));
    }

    @Override
    public void place(MenuBuilder builder, MenuSlot slot) {
        builder.setSlot(index, slot);
    }
}
