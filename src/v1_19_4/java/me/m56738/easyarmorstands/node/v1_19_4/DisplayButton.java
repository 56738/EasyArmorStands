package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.node.MenuNode;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.SimpleButton;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.joml.Vector3dc;

public class DisplayButton extends SimpleButton {
    private final Session session;
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayButton(Session session, Display entity, JOMLMapper mapper) {
        super(session);
        this.session = session;
        this.entity = entity;
        this.mapper = mapper;
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
        DisplayBone bone = new DisplayBone(entity, mapper);

        MenuNode localNode = new DisplayRootNode(session, Component.text("Local"), entity);
        localNode.setRoot(true);
        localNode.addMoveButtons(session, bone, 2, false);
        localNode.addRotationButtons(session, bone, 1, true);
        localNode.addScaleNodes(session, bone, 2);

        MenuNode globalNode = new DisplayRootNode(session, Component.text("Global"), entity);
        globalNode.setRoot(true);
        globalNode.addPositionButtons(session, bone, 3, true);
        globalNode.addRotationButtons(session, bone, 1, false);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
