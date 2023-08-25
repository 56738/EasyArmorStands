package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.ItemTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DestroySlotFactory implements MenuSlotFactory {
    private final ItemTemplate itemTemplate;

    public DestroySlotFactory(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Element element = context.element();
        if (element instanceof DestroyableElement && context.permissions().test("easyarmorstands.destroy")) {
            return new DestroySlot((DestroyableElement) element, itemTemplate, context.resolver());
        } else {
            return null;
        }
    }
}
