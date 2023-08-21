package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
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
        this.blockDataProperty = container.getOrNull(DisplayPropertyTypes.BLOCK_DISPLAY_BLOCK);
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
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission("easyarmorstands.open")) {
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
