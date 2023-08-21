package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.RotationBone;
import me.m56738.easyarmorstands.api.editor.bone.RotationProvider;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniond;
import org.joml.Vector3d;

public class BoneRotationNode extends RotationNode {
    private final RotationBone bone;
    private final Component name;
    private final RotationProvider rotationProvider;
    private final Axis axis;
    private final Quaterniond initial = new Quaterniond();
    private final Quaterniond current = new Quaterniond();

    public BoneRotationNode(Session session, RotationBone bone, Component name, Axis axis, ParticleColor color, double radius, RotationProvider rotationProvider) {
        super(session, color, new Vector3d(), axis, radius);
        this.bone = bone;
        this.name = name;
        this.axis = axis;
        this.rotationProvider = rotationProvider;
    }

    @Override
    public void onEnter(EnterContext context) {
        super.onEnter(context);
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
            getRotation().set(rotationProvider.getRotation());
        }
    }

    @Override
    protected void apply(double angle, double degrees) {
        Vector3d axis = this.axis.getDirection().rotate(getRotation(), new Vector3d());
        bone.setRotation(current.setAngleAxis(angle, axis).mul(initial));
    }

    @Override
    protected void commit() {
        bone.commit();
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public boolean isValid() {
        return bone.isValid();
    }
}
