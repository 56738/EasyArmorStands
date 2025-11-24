package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.display.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.node.ToolMenuModeSwitcher;
import me.m56738.easyarmorstands.lib.joml.Quaternionf;
import me.m56738.easyarmorstands.lib.joml.Vector3f;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode, ResettableNode {
    private final Session session;
    private final DisplayElement<?> element;
    private final Property<Location> locationProperty;
    private final Property<BlockData> blockDataProperty;
    private final ToolMenuManager toolManager;
    private final ToolMenuModeSwitcher toolSwitcher;
    private final boolean allowMenu;

    public DisplayRootNode(Session session, DisplayElement<?> element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        this.blockDataProperty = properties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
        this.toolManager = new ToolMenuManager(session, this, element.getTools(properties()));
        this.toolSwitcher = new ToolMenuModeSwitcher(this.toolManager);
        this.allowMenu = session.player().hasPermission(Permissions.OPEN);
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
        return false;
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        super.onUpdate(context);
        context.setActionBar(toolSwitcher.getActionBar());
        toolSwitcher.onUpdate(context);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(session, element));
        }
        context.addInput(new ReturnInput(session));
    }

    @Override
    public @NotNull Element getElement() {
        return element;
    }

    @Override
    public void reset() {
        properties().get(DisplayPropertyTypes.TRANSLATION).setValue(new Vector3f());
        properties().get(DisplayPropertyTypes.LEFT_ROTATION).setValue(new Quaternionf());
        properties().get(DisplayPropertyTypes.SCALE).setValue(new Vector3f(1));
        properties().get(DisplayPropertyTypes.RIGHT_ROTATION).setValue(new Quaternionf());

        Location location = locationProperty.getValue();
        location.setYaw(0);
        location.setPitch(0);
        locationProperty.setValue(location);

        properties().commit();
    }
}
