package me.m56738.easyarmorstands.fancyholograms.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.node.AbstractPropertyNode;
import me.m56738.easyarmorstands.editor.node.ToolMenuModeSwitcher;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HologramRootNode extends AbstractPropertyNode implements ElementNode {
    private final Session session;
    private final HologramElement element;
    private final ToolMenuModeSwitcher toolSwitcher;
    private final Property<BlockData> blockDataProperty;

    public HologramRootNode(Session session, HologramElement element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.toolSwitcher = new ToolMenuModeSwitcher(
                new ToolMenuManager(session, this, element.getTools(properties())));
        this.blockDataProperty = properties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        Player player = session.player();
        if (blockDataProperty != null && context.type() == ClickContext.Type.LEFT_CLICK && player.isSneaking()) {
            Block block = context.block();
            if (block != null) {
                BlockData blockData = block.getBlockData();
                if (blockDataProperty.setValue(blockData)) {
                    properties().commit();
                    new EasPlayer(player).sendMessage(Message.success("easyarmorstands.success.changed-block",
                            blockDataProperty.getType().getValueComponent(blockData)));
                    return true;
                }
            }
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            element.openMenu(player);
            return true;
        }
        return toolSwitcher.onClick(context);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolSwitcher.getActionBar());
    }

    @Override
    public @NotNull HologramElement getElement() {
        return element;
    }
}
