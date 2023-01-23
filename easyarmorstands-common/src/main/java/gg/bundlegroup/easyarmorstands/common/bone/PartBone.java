package gg.bundlegroup.easyarmorstands.common.bone;

import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PartBone extends AbstractBone {
    private static final double NORMAL_SCALE = 1.0 / 16;
    private static final double SMALL_SCALE = NORMAL_SCALE / 2;
    private static final Vector3d pose = new Vector3d();
    private static final Matrix3d poseMatrix = new Matrix3d();
    private final Session session;
    private final EasArmorStand.Part part;
    private final Component component;
    private final Vector3d normalOffset;
    private final Vector3d normalLength;
    private final Vector3d smallOffset;
    private final Vector3d smallLength;

    private final Vector3d start = new Vector3d();
    private final Vector3d end = new Vector3d();
    private final Matrix3d rotation = new Matrix3d();

    public PartBone(Session session, EasArmorStand.Part part, Component component, Vector3dc offset, Vector3dc length) {
        super(session);
        this.session = session;
        this.part = part;
        this.component = component;
        this.normalOffset = offset.mul(NORMAL_SCALE, new Vector3d());
        this.normalLength = length.mul(NORMAL_SCALE, new Vector3d());
        this.smallOffset = offset.mul(SMALL_SCALE, new Vector3d());
        this.smallLength = length.mul(SMALL_SCALE, new Vector3d());
    }

    private Vector3dc getOffset(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return normalOffset;
        } else {
            return smallOffset;
        }
    }

    private Vector3dc getLength(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return normalLength;
        } else {
            return smallLength;
        }
    }

    @Override
    public void refresh() {
        EasArmorStand entity = session.getEntity();
        Matrix3dc yaw = session.getArmorStandYaw();
        yaw.transform(getOffset(entity), start).add(entity.getPosition());
        yaw.mul(Util.fromEuler(entity.getPose(part, pose), poseMatrix), rotation);
        rotation.transform(getLength(entity), end).add(start);
    }

    @Override
    public Vector3dc getPosition() {
        return end;
    }

    @Override
    public Component subtitle() {
        return component;
    }

    public EasArmorStand.Part getPart() {
        return part;
    }

    public Vector3dc getAnchor() {
        return start;
    }

    public Matrix3dc getRotation() {
        return rotation;
    }

    public void setRotation(Matrix3dc rotation) {
        this.rotation.set(rotation);
        Util.toEuler(poseMatrix.setTransposed(session.getArmorStandYaw()).mul(rotation), pose);
        session.getEntity().setPose(part, pose);
    }
}
