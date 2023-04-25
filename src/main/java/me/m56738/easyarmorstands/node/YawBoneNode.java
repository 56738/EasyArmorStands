package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.YawBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Vector3d;

public class YawBoneNode extends RotationNode {
    private final YawBone bone;
    private final Component name;
    private float initialYaw;

    public YawBoneNode(Session session, Component name, TextColor color, double radius, YawBone bone) {
        super(session, color, bone.getPosition(), new Vector3d(0, 1, 0), radius);
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
