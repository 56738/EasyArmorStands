package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.jetbrains.annotations.NotNull;

public class BackgroundSlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate itemTemplate;

    public BackgroundSlotFactory(SimpleItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public BackgroundSlot createSlot(@NotNull MenuSlotContext context) {
        return new BackgroundSlot(itemTemplate, context.resolver());
    }
}
