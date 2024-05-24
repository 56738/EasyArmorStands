package me.m56738.easyarmorstands.menu.factory;

import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuFactoryBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MenuFactoryBuilderImpl implements MenuFactoryBuilder {
    private final Map<Integer, MenuSlotFactory> slotFactories = new TreeMap<>();
    private String titleTemplate;
    private int height;
    private MenuSlotFactory background;

    @Override
    public MenuSlotFactory setSlot(int slot, MenuSlotFactory factory) {
        MenuSlotFactory old = slotFactories.put(slot, factory);
        height = Math.max(height, slot / 9 + 1);
        return old;
    }

    @Override
    public void setTitleTemplate(@NotNull String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    @Override
    public void setHeight(int height) {
        if (this.height < height) {
            this.height = height;
        }
    }

    @Override
    public void setBackground(MenuSlotFactory background) {
        this.background = background;
    }

    @Override
    public @NotNull MenuFactory build() {
        Objects.requireNonNull(titleTemplate, "Title not set");
        MenuSlotFactory[] slots = new MenuSlotFactory[height * 9];
        for (Map.Entry<Integer, MenuSlotFactory> entry : slotFactories.entrySet()) {
            slots[entry.getKey()] = entry.getValue();
        }
        return new MenuFactoryImpl(titleTemplate, slots, background);
    }
}
