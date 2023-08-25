package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.display.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode {
    private final Session session;
    private final DisplayElement<?> element;
    private final Property<BlockData> blockDataProperty;

    public DisplayRootNode(Session session, Component name, DisplayElement<?> element) {
        super(session, name, session.properties(element));
        this.session = session;
        this.element = element;
        this.blockDataProperty = container.getOrNull(BlockDisplayPropertyTypes.BLOCK);
    }

    @Override
    public boolean onClick(ClickContext context) {
        Player player = session.player();
        if (blockDataProperty != null && context.type() == ClickContext.Type.LEFT_CLICK && player.isSneaking()) {
            Block block = context.block();
            if (block != null) {
                if (blockDataProperty.setValue(block.getBlockData())) {
                    container.commit();
                    return true;
                }
            }
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            element.openMenu(player);
            return true;
        }
        return super.onClick(context);
    }

    @Override
    public DisplayElement<?> getElement() {
        return element;
    }
}
