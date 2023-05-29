package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3dc;

public class PositionBoneButton extends SimpleButton implements NodeFactory {
    private final PositionBone bone;
    private final Node node;
    private final Component name;

    public PositionBoneButton(Session session, PositionBone bone, Node node, Component name, RGBLike color) {
        super(session, color);
        this.bone = bone;
        this.node = node;
        this.name = name;
    }

    @Override
    protected Vector3dc getPosition() {
        return bone.getPosition();
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Node createNode() {
        return node;
    }
}
