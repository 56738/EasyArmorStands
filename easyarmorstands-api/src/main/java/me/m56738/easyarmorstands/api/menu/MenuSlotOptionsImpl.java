package me.m56738.easyarmorstands.api.menu;

record MenuSlotOptionsImpl() implements MenuSlotOptions {
    static final MenuSlotOptions EMPTY = new Builder().build();

    static class Builder implements MenuSlotOptions.Builder {
        @Override
        public MenuSlotOptions build() {
            return new MenuSlotOptionsImpl();
        }
    }
}
