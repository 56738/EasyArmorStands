package me.m56738.easyarmorstands.fancyholograms.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractElementNode;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.common.editor.node.ToolModeSwitcher;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElement;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class HologramRootNode extends AbstractElementNode<HologramElement> implements ElementNode {
    private final ToolModeSwitcher toolModeSwitcher;
    private final @Nullable Property<BlockData> blockDataProperty;

    public HologramRootNode(Session session, HologramElement element) {
        super(session, element);
        this.toolModeSwitcher = new ToolModeSwitcher(
                new ToolManager(session, this, element.getTools(getContext())));
        this.blockDataProperty = getProperties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
        Player player = getSession().player();
        if (blockDataProperty != null && context.type() == ClickContext.Type.LEFT_CLICK && player.isSneaking()) {
            Block block = context.block();
            if (block != null) {
                BlockData blockData = block.getBlockData();
                if (blockDataProperty.setValue(blockData)) {
                    getContext().commit();
                    player.sendMessage(Message.success("easyarmorstands.success.changed-block",
                            blockDataProperty.getType().getValueComponent(blockData)));
                    return true;
                }
            }
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            getElement().openMenu(player);
            return true;
        }
        return toolModeSwitcher.onClick(context);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolModeSwitcher.getActionBar());
    }
}
