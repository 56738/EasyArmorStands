package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;

@FunctionalInterface
public interface MenuButtonFactory {
    MenuButton create(EasyArmorStandsCommon eas);
}
