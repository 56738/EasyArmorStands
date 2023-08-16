package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.v1_19_4.display.block.BlockDisplayBlockProperty;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.joml.Vector3dc;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode {
    private final Session session;
    private final DisplayElement<?> element;
    private final Property<BlockData> blockDataProperty;

    public DisplayRootNode(Session session, Component name, DisplayElement<?> element) {
        super(session, name, PropertyContainer.tracked(session.getPlayer(), element));
        this.session = session;
        this.element = element;
        this.blockDataProperty = container.getOrNull(BlockDisplayBlockProperty.TYPE);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        EasPlayer player = session.getPlayer();
        if (blockDataProperty != null && context.getType() == ClickType.LEFT_CLICK && player.isSneaking()) {
            Block block = context.getBlock();
            if (block != null) {
                if (blockDataProperty.setValue(block.getBlockData())) {
                    container.commit();
                    return true;
                }
            }
        }
        if (context.getType() == ClickType.LEFT_CLICK && player.permissions().test("easyarmorstands.open")) {
            element.openMenu(player);
            return true;
        }
        return super.onClick(eyes, target, context);
    }

    @Override
    public DisplayElement<?> getElement() {
        return element;
    }
}
