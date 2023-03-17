package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.bone.v1_19_4.DisplayBone;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.node.ParentNode;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

import java.util.function.Supplier;

public class DisplayNodeFactory implements Supplier<Node> {
    private final Session session;
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayNodeFactory(Session session, Display entity, JOMLMapper mapper) {
        this.session = session;
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public Node get() {
        DisplayBone bone = new DisplayBone(entity, mapper);

        ParentNode localNode = new ParentNode(session, Component.text("Local"));
        localNode.setRoot(true);
        localNode.addMoveNodes(session, bone, 2, false);
        localNode.addRotationNodes(session, bone, 1, true);
        localNode.addScaleNodes(session, bone, 2);

        ParentNode globalNode = new ParentNode(session, Component.text("Global"));
        globalNode.setRoot(true);
        globalNode.addPositionNodes(session, bone, 3, true);
        globalNode.addRotationNodes(session, bone, 1, false);

        localNode.setNextNode(globalNode);
        globalNode.setNextNode(localNode);

        return localNode;
    }
}
