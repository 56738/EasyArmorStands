package me.m56738.easyarmorstands.menu.layout;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.menu.MenuImpl;
import net.kyori.adventure.text.Component;

import java.util.Locale;

public abstract class MenuBuilder implements MenuLayout {
    private final Component title;
    private final Locale locale;
    private final MenuSlot background;
    private final Int2ObjectMap<MenuSlot> slots = new Int2ObjectOpenHashMap<>();
    private int height = 1;

    public MenuBuilder(Component title, Locale locale, MenuSlot background) {
        this.title = title;
        this.locale = locale;
        this.background = background;
    }

    private static int getIndex(int row, int column) {
        return row * 9 + column;
    }

    public void setSlot(int row, int column, MenuSlot slot) {
        slots.put(getIndex(row, column), slot);
        expandHeight(row + 1);
    }

    public void expandHeight(int minHeight) {
        if (height < minHeight) {
            height = minHeight;
        }
    }

    @Override
    public Menu createMenu() {
        MenuSlot[] menuSlots = new MenuSlot[height * 9];
        for (int i = 0; i < menuSlots.length; i++) {
            menuSlots[i] = slots.getOrDefault(i, background);
        }
        return new MenuImpl(title, menuSlots, locale);
    }
}
