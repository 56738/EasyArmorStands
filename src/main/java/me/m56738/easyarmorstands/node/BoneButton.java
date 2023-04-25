package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneButton extends SimpleButton {
    private final Bone bone;
    private final Node node;
    private final Component name;

    public BoneButton(Session session, Bone bone, Node node, Component name) {
        super(session);
        this.bone = bone;
        this.node = node;
        this.name = name;
    }

    @Override
    protected Vector3dc getPosition() {
        return bone.getMatrix().getTranslation(new Vector3d());
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
