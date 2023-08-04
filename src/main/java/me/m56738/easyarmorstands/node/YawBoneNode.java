package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.PositionAndYawBone;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Axis;
import net.kyori.adventure.text.Component;

public class YawBoneNode extends RotationNode {
    private final PositionAndYawBone bone;
    private final Component name;
    private float initialYaw;

    public YawBoneNode(Session session, Component name, ParticleColor color, double radius, PositionAndYawBone bone) {
        super(session, color, bone.getPosition(), Axis.Y, radius);
        this.name = name;
        this.bone = bone;
    }

    @Override
    public void onEnter() {
        super.onEnter();
        initialYaw = bone.getYaw();
    }

    @Override
    protected void abort() {
        bone.setYaw(initialYaw);
    }

    @Override
    protected void refresh() {
        getAnchor().set(bone.getPosition());
    }

    @Override
    protected void apply(double angle, double degrees) {
        bone.setYaw(initialYaw - (float) degrees);
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
