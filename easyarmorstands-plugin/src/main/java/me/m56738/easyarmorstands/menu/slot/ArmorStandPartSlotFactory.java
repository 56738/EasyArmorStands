package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandPartSlotFactory implements MenuSlotFactory {
    private final ArmorStandPart part;
    private final SimpleItemTemplate itemTemplate;

    public ArmorStandPartSlotFactory(ArmorStandPart part, SimpleItemTemplate itemTemplate) {
        this.part = part;
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Session session = context.session();
        if (session == null) {
            return null;
        }
        ArmorStandRootNode root = session.findNode(ArmorStandRootNode.class);
        if (root == null) {
            return null;
        }
        return new NodeSlot(session,
                root.getPartButton(part),
                ArmorStandPartResetAction.pose(part, context.properties(root.getElement())),
                itemTemplate,
                context.resolver());
    }
}
