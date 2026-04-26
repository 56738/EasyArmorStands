package me.m56738.easyarmorstands.fancyholograms.editor.layer;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.layer.ElementLayer;
import me.m56738.easyarmorstands.editor.util.ToolMenuManager;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.input.OpenElementMenuInput;
import me.m56738.easyarmorstands.editor.input.ReturnInput;
import me.m56738.easyarmorstands.editor.layer.PropertyLayer;
import me.m56738.easyarmorstands.editor.layer.ToolMenuModeSwitcher;
import me.m56738.easyarmorstands.fancyholograms.element.HologramElement;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HologramRootLayer extends PropertyLayer implements ElementLayer {
    private final Session session;
    private final HologramElement element;
    private final ToolMenuModeSwitcher toolSwitcher;
    private final Property<BlockData> blockDataProperty;
    private final boolean allowMenu;

    public HologramRootLayer(Session session, HologramElement element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.toolSwitcher = new ToolMenuModeSwitcher(
                new ToolMenuManager(session, this, element.getTools(properties())));
        this.blockDataProperty = properties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
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
        context.setActionBar(toolSwitcher.getActionBar());
        toolSwitcher.onUpdate(context);
        if (allowMenu) {
            context.addInput(new OpenElementMenuInput(session, element));
        }
        super.onUpdate(context);
        context.addInput(new ReturnInput(session));
    }

    @Override
    public @NotNull HologramElement getElement() {
        return element;
    }
}
