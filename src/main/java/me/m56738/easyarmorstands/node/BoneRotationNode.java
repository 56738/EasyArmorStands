package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.RotationBone;
import me.m56738.easyarmorstands.bone.RotationProvider;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneRotationNode extends RotationNode {
    private final RotationBone bone;
    private final Component name;
    private final RotationProvider rotationProvider;
    private final Vector3d axis;
    private final Quaterniond initial = new Quaterniond();
    private final Quaterniond current = new Quaterniond();

    public BoneRotationNode(Session session, RotationBone bone, Component name, Vector3dc axis, TextColor color, double radius, RotationProvider rotationProvider) {
        super(session, color, new Vector3d(), axis, radius);
        this.bone = bone;
        this.name = name;
        this.axis = new Vector3d(axis);
        this.rotationProvider = rotationProvider;
    }

    @Override
    public void onEnter() {
        super.onEnter();
        initial.set(bone.getRotation());
    }

    @Override
    protected void abort() {
        bone.setRotation(initial);
    }

    @Override
    protected void refresh() {
        getAnchor().set(bone.getAnchor());
        if (rotationProvider != null) {
            rotationProvider.getRotation().transform(axis, getAxis()).normalize();
        }
    }

    @Override
    protected void apply(double angle, double degrees) {
        Vector3d axis = getAxis();
        bone.setRotation(current.setAngleAxis(angle, axis).mul(initial));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && bone.isValid();
    }
}
