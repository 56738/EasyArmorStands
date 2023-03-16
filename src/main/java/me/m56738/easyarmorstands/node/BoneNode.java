package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneNode extends ButtonNode {
    private final Bone bone;
    private final Component name;

    public BoneNode(Session session, Node node, Bone bone, Component name) {
        super(session, node);
        this.bone = bone;
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
}
