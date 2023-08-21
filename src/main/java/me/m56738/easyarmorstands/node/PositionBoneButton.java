package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.PositionBone;
import me.m56738.easyarmorstands.api.editor.bone.RotationProvider;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public class PositionBoneButton extends SimpleButton implements NodeButton {
    private final PositionBone bone;
    private final me.m56738.easyarmorstands.api.editor.node.Node node;
    private final Component name;

    public PositionBoneButton(Session session, PositionBone bone, me.m56738.easyarmorstands.api.editor.node.Node node, Component name, ParticleColor color) {
        super(session, color);
        this.bone = bone;
        this.node = node;
        this.name = name;
        if (bone instanceof RotationProvider) {
            particle.setBillboard(false);
        }
    }

    @Override
    public void update() {
        super.update();
        if (bone instanceof RotationProvider) {
            particle.setRotation(((RotationProvider) bone).getRotation());
        }
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
