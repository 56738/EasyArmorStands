package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlot;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 3 columns on the left for utilities.
 * 5 columns on the right for properties.
 */
public class SplitMenuBuilder implements MenuBuilder {
    private static final List<MenuSlot> emptyRow = new ArrayList<>(9);

    static {
        for (int i = 0; i < 9; i++) {
            emptyRow.add(null);
        }
    }

    private final ArrayList<MenuSlot> slots = new ArrayList<>();

    public void ensureRow(int row) {
        while (slots.size() <= 9 * row) {
            slots.addAll(emptyRow);
        }
    }

    @Override
    public void addButton(MenuSlot slot) {
        for (int row = 0; row < 6; row++) {
            ensureRow(row);
            for (int column = 8; column > 3; column--) {
                int index = 9 * row + column;
                if (slots.get(index) == null) {
                    slots.set(index, slot);
                    return;
                }
            }
        }
    }

    @Override
    public void addUtility(MenuSlot slot) {
        for (int row = 0; row < 6; row++) {
            ensureRow(row);
            for (int column = 0; column < 3; column++) {
                int index = 9 * row + column;
                if (slots.get(index) == null) {
                    slots.set(index, slot);
                    return;
                }
            }
        }
    }

    public void setSlot(int index, MenuSlot slot) {
        ensureRow(index / 9);
        slots.set(index, slot);
    }

    @Override
    public Menu build(Component title, Locale locale) {
        MenuSlot[] slots = this.slots.toArray(new MenuSlot[0]);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = BackgroundSlot.INSTANCE;
            }
        }
        return new Menu(title, slots, locale);
    }
}
