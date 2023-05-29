package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.node.AxisAlignedBoxButton;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayButton<T extends Display> extends AxisAlignedBoxButton {
    private final Session session;
    private final T entity;
    private final DisplayAddon addon;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayButton(Session session, T entity, DisplayAddon addon, DisplayRootNodeFactory<T> factory) {
        super(session);
        this.session = session;
        this.entity = entity;
        this.addon = addon;
        this.factory = factory;
    }

    @Override
    protected Vector3dc getPosition() {
        Location location = entity.getLocation();
        return new Vector3d(location.getX(), location.getY() + entity.getDisplayHeight() / 2, location.getZ());
    }

    @Override
    protected Vector3dc getSize() {
        double width = entity.getDisplayWidth();
        double height = entity.getDisplayHeight();
        return new Vector3d(width, height, width);
    }

    @Override
    public Component getName() {
        return Component.text(Util.getId(entity.getUniqueId()));
    }

    @Override
    public Node createNode() {
        DisplayBone bone = new DisplayBone(session, entity, addon, addon.getDisplayLeftRotationProperty());

        MenuNode localNode = factory.createRootNode(session, Component.text("Local"), entity);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, bone, bone, 2, false);
        localNode.addCarryButtonWithYaw(session, bone);
        localNode.addRotationButtons(session, bone, 1, bone);
        localNode.addScaleButtons(session, bone, 2);

        MenuNode globalNode = factory.createRootNode(session, Component.text("Global"), entity);
        globalNode.setRoot(true);
        globalNode.addPositionButtons(session, bone, 3, true);
        globalNode.addCarryButtonWithYaw(session, bone);
        globalNode.addRotationButtons(session, bone, 1, null);
        globalNode.addYawButton(session, bone, 1.5);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
