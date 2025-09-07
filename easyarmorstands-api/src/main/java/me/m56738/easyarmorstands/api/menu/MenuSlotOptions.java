package me.m56738.easyarmorstands.api.menu;

public interface MenuSlotOptions {
    static Builder builder() {
        return new MenuSlotOptionsImpl.Builder();
    }

    static MenuSlotOptions empty() {
        return MenuSlotOptionsImpl.EMPTY;
    }

    interface Builder {
        MenuSlotOptions build();
    }
}
