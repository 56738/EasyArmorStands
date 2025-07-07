package me.m56738.easyarmorstands.menu.layout;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public class DefaultMenuBuilder extends MenuBuilder {
    private int nextRow;
    private int nextColumn;

    public DefaultMenuBuilder(Component title, Locale locale, MenuSlot background) {
        super(title, locale, background);
    }

    @Override
    public void addSlot(MenuSlot slot) {
        setSlot(nextRow, nextColumn, slot);
        nextColumn++;
        if (nextColumn > 8) {
            nextColumn = 0;
            nextRow++;
        }
    }
}
