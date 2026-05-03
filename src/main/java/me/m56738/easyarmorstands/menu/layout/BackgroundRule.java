package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;

record BackgroundRule(MenuButton fallback) implements MenuLayoutRule {
    @Override
    public boolean matches(MenuButton button) {
        return false;
    }
}
