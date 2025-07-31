package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolManager;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.common.element.DisplayElement;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

public class DisplayRootNode extends DisplayNode implements ElementNode, ResettableNode {
    private final Session session;
    private final DisplayElement element;
    private final Property<Location> locationProperty;
    private final @Nullable Property<BlockData> blockDataProperty;
    private final ToolManager toolManager;
    private final ToolModeSwitcher toolModeSwitcher;

    public DisplayRootNode(Session session, DisplayElement element) {
        super(session, element);
        this.session = session;
        this.element = element;
        this.locationProperty = getProperties().get(EntityPropertyTypes.LOCATION);
        this.blockDataProperty = getProperties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
        this.toolManager = new ToolManager(session, this, element.getTools(getContext()));
        this.toolModeSwitcher = new ToolModeSwitcher(this.toolManager);
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
                    getContext().commit();
                    player.sendMessage(Message.success("easyarmorstands.success.changed-block",
                            blockDataProperty.getType().getValueComponent(blockData)));
                    return true;
                }
            }
        }
        if (context.type() == ClickContext.Type.LEFT_CLICK && player.hasPermission(Permissions.OPEN)) {
            element.openMenu(player);
            return true;
        }
        return toolModeSwitcher.onClick(context);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolModeSwitcher.getActionBar());
    }

    @Override
    public void reset() {
        getProperties().get(DisplayPropertyTypes.TRANSLATION).setValue(new Vector3f());
        getProperties().get(DisplayPropertyTypes.LEFT_ROTATION).setValue(new Quaternionf());
        getProperties().get(DisplayPropertyTypes.SCALE).setValue(new Vector3f(1));
        getProperties().get(DisplayPropertyTypes.RIGHT_ROTATION).setValue(new Quaternionf());

        locationProperty.setValue(locationProperty.getValue().withRotation(0, 0));

        getContext().commit();
    }
}
