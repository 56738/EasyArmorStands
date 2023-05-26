package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayPositionBone;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayRotationBone;
import me.m56738.easyarmorstands.bone.v1_19_4.DisplayScaleBone;
import me.m56738.easyarmorstands.node.AxisAlignedBoxButton;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.session.Session;
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
        return new Vector3d(location.getX(), location.getY() + getHeight() / 2, location.getZ());
    }

    @Override
    protected double getWidth() {
        return entity.getDisplayWidth();
    }

    @Override
    protected double getHeight() {
        return entity.getDisplayHeight();
    }

    @Override
    public Component getName() {
        return Component.text(entity.getUniqueId().toString());
    }

    @Override
    public Node createNode() {
        DisplayPositionBone positionBone = new DisplayPositionBone(session, entity, addon);
        DisplayRotationBone rotationBone = new DisplayRotationBone(session, entity, addon, addon.getDisplayLeftRotationProperty());
        DisplayScaleBone scaleBone = new DisplayScaleBone(session, entity, addon);

        MenuNode localNode = factory.createRootNode(session, Component.text("Local"), entity);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, positionBone, rotationBone, 2, false);
        localNode.addRotationButtons(session, rotationBone, 1, rotationBone);
        localNode.addScaleButtons(session, scaleBone, 2);

        MenuNode globalNode = factory.createRootNode(session, Component.text("Global"), entity);
        globalNode.setRoot(true);
        globalNode.addPositionButtons(session, positionBone, 3, true);
        globalNode.addRotationButtons(session, rotationBone, 1, null);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
