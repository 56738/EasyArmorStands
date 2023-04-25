package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public class ArmorStandPartButton extends SimpleButton {
    private final ArmorStandPartBone bone;
    private final Node node;

    public ArmorStandPartButton(Session session, ArmorStandPartBone bone, Node node) {
        super(session);
        this.bone = bone;
        this.node = node;
    }

    @Override
    protected Vector3dc getPosition() {
        return bone.getEnd();
    }

    @Override
    public Component getName() {
        return bone.getPart().getName();
    }

    @Override
    public Node createNode() {
        return node;
    }
}
