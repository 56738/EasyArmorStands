package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.ArmorStandPartBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public class ArmorStandPartNode extends ButtonNode {
    private final ArmorStandPartBone bone;

    public ArmorStandPartNode(Session session, Node node, ArmorStandPartBone bone) {
        super(session, node);
        this.bone = bone;
    }

    @Override
    protected Vector3dc getPosition() {
        return bone.getEnd();
    }

    @Override
    public Component getName() {
        return bone.getPart().getName();
    }
}
