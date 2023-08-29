package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.menu.MenuButton;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.display.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.display.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.display.editor.axis.DisplayGlobalRotateAxis;
import me.m56738.easyarmorstands.display.editor.axis.DisplayLocalRotateAxis;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.editor.axis.LocationMoveAxis;
import me.m56738.easyarmorstands.editor.axis.LocationRelativeMoveAxis;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode {
    private final Session session;
    private final DisplayElement<?> element;
    private final Property<BlockData> blockDataProperty;
    private final DisplayOffsetProvider offsetProvider;
    private final DisplayRotationProvider rotationProvider;
    private final List<MenuButton> buttons = new ArrayList<>();
    private boolean local;
    private Component name;

    public DisplayRootNode(Session session, DisplayElement<?> element) {
        super(session, session.properties(element));
        this.session = session;
        this.element = element;
        this.blockDataProperty = container.getOrNull(BlockDisplayPropertyTypes.BLOCK);
        this.offsetProvider = new DisplayOffsetProvider(container);
        this.rotationProvider = new DisplayRotationProvider(container);
        setLocal(true);
    }

    public void setLocal(boolean local) {
        this.local = local;

        if (local) {
            name = Message.component("easyarmorstands.node.local");
        } else {
            name = Message.component("easyarmorstands.node.global");
        }

        for (MenuButton button : buttons) {
            removeButton(button);
        }

        buttons.clear();

        for (Axis axis : Axis.values()) {
            MoveAxis moveAxis;
            RotateAxis rotateAxis;
            if (local) {
                moveAxis = new LocationRelativeMoveAxis(container, axis, offsetProvider, rotationProvider);
                rotateAxis = new DisplayLocalRotateAxis(container, axis);
            } else {
                moveAxis = new LocationMoveAxis(container, axis, offsetProvider);
                rotateAxis = new DisplayGlobalRotateAxis(container, axis);
            }
            buttons.add(session.menuEntryProvider()
                    .move()
                    .setAxis(moveAxis)
                    .build());
            buttons.add(session.menuEntryProvider()
                    .rotate()
                    .setAxis(rotateAxis)
                    .build());
            // TODO scale
        }

        for (MenuButton button : buttons) {
            addButton(button);
        }
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }
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
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            setLocal(!local);
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate(UpdateContext context) {
        super.onUpdate(context);
        session.setActionBar(name);
    }

    @Override
    public DisplayElement<?> getElement() {
        return element;
    }
}
