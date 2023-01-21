package gg.bundlegroup.easyarmorstands.handle;

import gg.bundlegroup.easyarmorstands.manipulator.BoneAimManipulator;
import gg.bundlegroup.easyarmorstands.manipulator.BoneAxisManipulator;
import gg.bundlegroup.easyarmorstands.manipulator.Manipulator;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.session.Session;
import gg.bundlegroup.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.List;

public class BoneHandle implements Handle {
    private static final double SCALE = 1.0 / 16;
    private static final Vector3d pose = new Vector3d();
    private static final Matrix3d poseMatrix = new Matrix3d();
    private final Session session;
    private final EasArmorStand.Part part;
    private final Component component;
    private final Vector3dc offset;
    private final Vector3dc length;
    private final Vector3dc smallOffset;
    private final Vector3dc smallLength;

    private final Matrix3d yaw = new Matrix3d();
    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Matrix3d rotation = new Matrix3d();

    private final List<Manipulator> manipulators = new ArrayList<>();

    public BoneHandle(Session session, EasArmorStand.Part part, Component component, Vector3d offset, Vector3d length) {
        this.session = session;
        this.part = part;
        this.component = component;
        this.offset = offset.mul(SCALE, new Vector3d());
        this.length = length.mul(SCALE, new Vector3d());
        this.smallOffset = this.offset.mul(0.5, new Vector3d());
        this.smallLength = this.length.mul(0.5, new Vector3d());
        this.manipulators.add(new BoneAimManipulator(this,
                "Aim", NamedTextColor.YELLOW));
        this.manipulators.add(new BoneAxisManipulator(this,
                "Y", NamedTextColor.GREEN,
                new Vector3d(0, 1, 0)));
        this.manipulators.add(new BoneAxisManipulator(this,
                "X", NamedTextColor.RED,
                new Vector3d(1, 0, 0)));
        this.manipulators.add(new BoneAxisManipulator(this,
                "Z", NamedTextColor.BLUE,
                new Vector3d(0, 0, 1)));
    }

    private Vector3dc getOffset(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return offset;
        } else {
            return smallOffset;
        }
    }

    private Vector3dc getLength(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return length;
        } else {
            return smallLength;
        }
    }

    @Override
    public void update() {
        EasArmorStand entity = session.getEntity();
        yaw.rotationY(-Math.toRadians(entity.getYaw()));
        yaw.transform(getOffset(entity), start).add(entity.getPosition());
        yaw.mul(Util.fromEuler(entity.getPose(part, pose), poseMatrix), rotation);
        rotation.transform(getLength(entity), end).add(start);
    }

    @Override
    public List<Manipulator> getManipulators() {
        return manipulators;
    }

    @Override
    public Vector3dc getPosition() {
        return end;
    }

    @Override
    public Component getComponent() {
        return component;
    }

    public Session getSession() {
        return session;
    }

    public Vector3dc getAnchor() {
        return start;
    }

    public Matrix3dc getRotation() {
        return rotation;
    }

    public void setRotation(Matrix3dc rotation) {
        this.rotation.set(rotation);
        Util.toEuler(poseMatrix.setTransposed(yaw).mul(rotation), pose);
        session.getEntity().setPose(part, pose);
    }
}
