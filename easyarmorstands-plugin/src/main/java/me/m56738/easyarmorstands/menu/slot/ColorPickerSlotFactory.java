package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorPickerSlotFactory implements MenuSlotFactory {
    private final ItemTemplate itemTemplate;
    private final ItemTemplate activeItemTemplate;

    public ColorPickerSlotFactory(ItemTemplate itemTemplate, ItemTemplate activeItemTemplate) {
        this.itemTemplate = itemTemplate;
        this.activeItemTemplate = activeItemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Element element = context.element();
        if (element instanceof MenuElement) {
            return new ColorPickerSlot(itemTemplate, activeItemTemplate, context.resolver(), (MenuElement) element);
        } else {
            return null;
        }
    }
}
