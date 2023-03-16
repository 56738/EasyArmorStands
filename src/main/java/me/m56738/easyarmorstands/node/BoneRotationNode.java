package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.MatrixBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneRotationNode extends RotationNode {
    private final MatrixBone bone;
    private final Component name;
    private final boolean local;
    private final Vector3d axis;
    private final Matrix3d initial = new Matrix3d();
    private final Matrix3d current = new Matrix3d();

    public BoneRotationNode(Session session, MatrixBone bone, Component name, Vector3dc axis, TextColor color, double radius, boolean local) {
        super(session, color, new Vector3d(), axis, radius);
        this.bone = bone;
        this.name = name;
        this.axis = new Vector3d(axis);
        this.local = local;
    }

    @Override
    public void onEnter() {
        super.onEnter();
        initial.set(bone.getMatrix());
    }

    @Override
    protected void abort() {
        Matrix4d matrix = new Matrix4d(bone.getMatrix());
        matrix.set3x3(initial);
        bone.setMatrix(matrix);
    }

    @Override
    protected void refresh() {
        Matrix4dc matrix = bone.getMatrix();
        matrix.getTranslation(getAnchor());
        if (local) {
            matrix.transformDirection(axis, getAxis()).normalize();
        }
    }

    @Override
    protected void apply(double angle, double degrees) {
        Vector3d axis = getAxis();
        initial.rotateLocal(angle, axis.x, axis.y, axis.z, current);

        Matrix4d matrix = new Matrix4d(bone.getMatrix());
        matrix.set3x3(current);
        bone.setMatrix(matrix);
    }

    @Override
    public Component getName() {
        return name;
    }
}
