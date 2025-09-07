package me.m56738.easyarmorstands.common.menu;

import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotOptions;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuBuilderImpl implements MenuBuilder {
    private final List<Entry> entries = new ArrayList<>();
    private Component title = Component.empty();

    @Override
    public void setTitle(Component title) {
        this.title = Objects.requireNonNull(title);
    }

    @Override
    public void addSlot(MenuSlot slot, MenuSlotOptions options) {
        entries.add(new Entry(slot, options));
    }

    @Override
    public Menu build() {
        return new MenuImpl(title);
    }

    private record Entry(MenuSlot slot, MenuSlotOptions options) {
    }
}
