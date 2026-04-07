package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.menu.MenuCreator;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;

import java.util.List;

public class DefaultMenuBuilder extends AbstractMenuBuilder {
    @Override
    protected void build(List<MenuButton> buttons, MenuCreator creator) {
        int i = 0;
        for (MenuButton button : buttons) {
            creator.setSlot(i, MenuButtonSlot.toSlot(button));
            i++;
        }
    }
}
