package me.m56738.easyarmorstands.display.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.editor.node.DisplayBoxNode;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.menu.slot.NodeSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayBoxSlotFactory implements MenuSlotFactory {
    private final SimpleItemTemplate itemTemplate;

    public DisplayBoxSlotFactory(SimpleItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Session session = context.session();
        if (session == null) {
            return null;
        }
        Element element = context.element();
        if (!(element instanceof DisplayElement<?>)) {
            return null;
        }
        PropertyContainer properties = context.properties();
        if (properties == null) {
            return null;
        }
        return new NodeSlot(
                session,
                () -> new DisplayBoxNode(session, properties),
                null,
                itemTemplate,
                context.resolver());
    }
}
