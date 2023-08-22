package me.m56738.easyarmorstands.menu.position;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;

public interface MenuSlotPosition {
    static MenuSlotPosition of(int index) {
        return new IndexPosition(index);
    }

    static MenuSlotPosition of(int row, int column) {
        return new IndexPosition(row, column);
    }

    static MenuSlotPosition button() {
        return ButtonPosition.INSTANCE;
    }

    static MenuSlotPosition utility() {
        return UtilityPosition.INSTANCE;
    }

    void place(MenuBuilder builder, MenuSlot slot);
}
