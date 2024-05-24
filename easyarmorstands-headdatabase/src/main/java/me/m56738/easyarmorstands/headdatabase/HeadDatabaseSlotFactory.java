package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.jetbrains.annotations.NotNull;

public class HeadDatabaseSlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate itemTemplate;

    public HeadDatabaseSlotFactory(SimpleItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public MenuSlot createSlot(@NotNull MenuSlotContext context) {
        if (context.permissions().test("headdb.open")) {
            return new HeadDatabaseSlot(itemTemplate, context.resolver());
        } else {
            return null;
        }
    }
}
