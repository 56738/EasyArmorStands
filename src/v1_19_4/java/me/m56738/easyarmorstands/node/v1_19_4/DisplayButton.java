package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.SimpleButton;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTransformationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.joml.Vector3dc;

public class DisplayButton<T extends Display> extends SimpleButton {
    private final Session session;
    private final T entity;
    private final JOMLMapper mapper;
    private final DisplayTransformationProperty transformationProperty;
    private final DisplayRootNodeFactory<T> factory;

    public DisplayButton(Session session, T entity, JOMLMapper mapper, DisplayTransformationProperty transformationProperty, DisplayRootNodeFactory<T> factory) {
        super(session);
        this.session = session;
        this.entity = entity;
        this.mapper = mapper;
        this.transformationProperty = transformationProperty;
        this.factory = factory;
    }

    @Override
    protected Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    @Override
    public Component getName() {
        return Component.text(entity.getUniqueId().toString());
    }

    @Override
    public Node createNode() {
        DisplayBone bone = new DisplayBone(session, entity, mapper, transformationProperty);

        MenuNode localNode = factory.createRootNode(session, Component.text("Local"), entity);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, bone, 2, false);
        localNode.addRotationButtons(session, bone, 1, true);
        localNode.addScaleButtons(session, bone, 2);

        MenuNode globalNode = factory.createRootNode(session, Component.text("Global"), entity);
        globalNode.setRoot(true);
        globalNode.addPositionButtons(session, bone, 3, true);
        globalNode.addRotationButtons(session, bone, 1, false);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
