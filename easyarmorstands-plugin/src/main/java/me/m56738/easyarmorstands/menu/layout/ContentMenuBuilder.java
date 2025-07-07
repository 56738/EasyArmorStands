package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.layout.ContentMenuLayout;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public class ContentMenuBuilder extends MenuBuilder implements ContentMenuLayout {
    private int nextRow;
    private int nextColumn = 4;

    public ContentMenuBuilder(Component title, Locale locale, MenuSlot background) {
        super(title, locale, background);
        expandHeight(4);
    }

    @Override
    public void setContentSlot(MenuSlot slot) {
        setSlot(2, 1, slot);
    }

    @Override
    public void addSlot(MenuSlot slot) {
        setSlot(nextRow, nextColumn, slot);
        nextColumn++;
        if (nextColumn > 8) {
            nextColumn = 4;
            nextRow++;
        }
    }
}
