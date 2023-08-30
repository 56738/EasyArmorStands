package me.m56738.easyarmorstands.display.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.ResettableNode;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.display.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.display.editor.axis.DisplayGlobalRotateAxis;
import me.m56738.easyarmorstands.display.editor.axis.DisplayLocalRotateAxis;
import me.m56738.easyarmorstands.display.element.DisplayElement;
import me.m56738.easyarmorstands.editor.axis.LocationMoveAxis;
import me.m56738.easyarmorstands.editor.axis.LocationRelativeMoveAxis;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode, ResettableNode {
    private final Session session;
    private final DisplayElement<?> element;
    private final Property<Location> locationProperty;
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
        this.locationProperty = properties().get(EntityPropertyTypes.LOCATION);
        this.blockDataProperty = properties().getOrNull(BlockDisplayPropertyTypes.BLOCK);
        this.offsetProvider = new DisplayOffsetProvider(properties());
        this.rotationProvider = new DisplayRotationProvider(properties());
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
                moveAxis = new LocationRelativeMoveAxis(properties(), axis, offsetProvider, rotationProvider);
                rotateAxis = new DisplayLocalRotateAxis(properties(), axis);
            } else {
                moveAxis = new LocationMoveAxis(properties(), axis, offsetProvider);
                rotateAxis = new DisplayGlobalRotateAxis(properties(), axis);
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
                    properties().commit();
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
