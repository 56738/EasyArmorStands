package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.menu.slot.BackgroundSlot;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MenuCreator {
    private final Locale locale;
    private final Map<Integer, MenuSlot> slots = new TreeMap<>();
    private Component title = Component.empty();
    private int height = 1;
    private MenuSlot background;

    public MenuCreator(Locale locale) {
        this.locale = locale;
    }

    public boolean isEmpty(int index) {
        return !slots.containsKey(index);
    }

    public MenuSlot setSlot(int index, MenuSlot slot) {
        MenuSlot old = slots.put(index, slot);
        if (!(slot instanceof BackgroundSlot)) {
            height = Math.max(height, index / 9 + 1);
        }
        return old;
    }

    public void setTitle(@NotNull Component title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (this.height < height) {
            this.height = height;
        }
    }

    public void setBackground(MenuSlot background) {
        this.background = background;
    }

    public @NotNull Menu build() {
        Objects.requireNonNull(title, "Title not set");
        MenuSlot[] slots = new MenuSlot[height * 9];
        for (Map.Entry<Integer, MenuSlot> entry : this.slots.entrySet()) {
            int index = entry.getKey();
            if (index >= slots.length) {
                continue;
            }
            slots[index] = entry.getValue();
        }
        for (int i = 0; i < height * 9; i++) {
            if (slots[i] == null) {
                slots[i] = background;
            }
        }
        return new MenuImpl(title, slots, locale);
    }
}
