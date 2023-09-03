package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.jetbrains.annotations.NotNull;

public class BackgroundSlotFactory implements MenuSlotFactory {
    private final ItemTemplate itemTemplate;

    public BackgroundSlotFactory(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public BackgroundSlot createSlot(@NotNull MenuSlotContext context) {
        return new BackgroundSlot(itemTemplate, context.resolver());
    }
}
