package me.m56738.easyarmorstands.menu.builder;

import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.slot.BackgroundSlot;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class SimpleMenuBuilder implements MenuBuilder {
    private static final List<MenuSlot> emptyRow = new ArrayList<>(9);

    static {
        for (int i = 0; i < 9; i++) {
            emptyRow.add(null);
        }
    }

    private final ArrayList<MenuSlot> slots = new ArrayList<>();
    private int currentIndex;

    private void ensureRow(int row) {
        while (slots.size() <= 9 * row) {
            slots.addAll(emptyRow);
        }
    }

    @Override
    public void addButton(MenuSlot slot) {
        if (currentIndex >= 6 * 9) {
            return;
        }
        ensureRow(currentIndex / 9);
        slots.set(currentIndex, slot);
        currentIndex++;
    }

    @Override
    public void addUtility(MenuSlot slot) {
        addButton(slot);
    }

    public void setSlot(int index, MenuSlot slot) {
        ensureRow(index / 9);
        slots.set(index, slot);
    }

    public int getSize() {
        return currentIndex;
    }

    public MenuSlot getSlot(int index) {
        return slots.get(index);
    }

    @Override
    public Menu build(Component title) {
        MenuSlot[] slots = this.slots.toArray(new MenuSlot[0]);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = BackgroundSlot.INSTANCE;
            }
        }
        return new Menu(title, slots);
    }
}
