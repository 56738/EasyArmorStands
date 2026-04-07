package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MenuCreator {
    private final Locale locale;
    private final Map<Integer, MenuSlot> slotFactories = new TreeMap<>();
    private String titleTemplate;
    private int height;
    private MenuSlot background;

    public MenuCreator(Locale locale) {
        this.locale = locale;
    }

    public MenuSlot setSlot(int slot, MenuSlot factory) {
        MenuSlot old = slotFactories.put(slot, factory);
        height = Math.max(height, slot / 9 + 1);
        return old;
    }

    public void setTitleTemplate(@NotNull String titleTemplate) {
        this.titleTemplate = titleTemplate;
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
        Objects.requireNonNull(titleTemplate, "Title not set");
        MenuSlot[] slots = new MenuSlot[height * 9];
        for (Map.Entry<Integer, MenuSlot> entry : slotFactories.entrySet()) {
            slots[entry.getKey()] = entry.getValue();
        }
        for (int i = 0; i < height * 9; i++) {
            if (slots[i] == null) {
                slots[i] = background;
            }
        }
        return new MenuImpl(MiniMessage.miniMessage().deserialize(titleTemplate), slots, locale);
    }
}
