package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.jetbrains.annotations.NotNull;

public class HeadDatabaseSlotFactory implements MenuSlotFactory {
    private final ItemTemplate itemTemplate;

    public HeadDatabaseSlotFactory(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public MenuSlot createSlot(@NotNull MenuSlotContext context) {
        return new HeadDatabaseSlot(itemTemplate, context.resolver());
    }
}
