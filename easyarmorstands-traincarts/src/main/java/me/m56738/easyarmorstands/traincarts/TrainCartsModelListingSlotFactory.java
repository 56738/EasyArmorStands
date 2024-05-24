package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrainCartsModelListingSlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate itemTemplate;

    public TrainCartsModelListingSlotFactory(SimpleItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Element element = context.element();
        if (element instanceof MenuElement && context.permissions().test(Permissions.TRAINCARTS_MODEL)) {
            return new TrainCartsModelListingSlot(itemTemplate, (MenuElement) element, context.resolver());
        } else {
            return null;
        }
    }
}
