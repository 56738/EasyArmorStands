package me.m56738.easyarmorstands.menu.factory;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MenuFactoryBuilder {
    private final Map<Integer, MenuSlotFactory> slotFactories = new TreeMap<>();
    private String titleTemplate;
    private int height;
    private MenuSlotFactory background;

    public MenuSlotFactory setSlot(int slot, MenuSlotFactory factory) {
        MenuSlotFactory old = slotFactories.put(slot, factory);
        height = Math.max(height, slot / 9 + 1);
        return old;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBackground(MenuSlotFactory background) {
        this.background = background;
    }

    public MenuFactory build() {
        Objects.requireNonNull(titleTemplate, "Title not set");
        MenuSlotFactory[] slots = new MenuSlotFactory[height * 9];
        for (Map.Entry<Integer, MenuSlotFactory> entry : slotFactories.entrySet()) {
            slots[entry.getKey()] = entry.getValue();
        }
        return new MenuFactoryImpl(titleTemplate, slots, background);
    }
}
