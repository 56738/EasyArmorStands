package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public enum BoneType {
    HEAD(
            EasArmorStand.Part.HEAD,
            "Head",
            new Vector3d(0, 23, 0),
            new Vector3d(0, 7, 0),
            new Matrix3d().rotationX(Math.PI / 2),
            Util.SWITCH_FORWARD_UP),
    BODY(
            EasArmorStand.Part.BODY,
            "Body",
            new Vector3d(0, 24, 0),
            new Vector3d(0, -12, 0),
            new Matrix3d().rotationX(-Math.PI / 2),
            Util.SWITCH_FORWARD_DOWN),
    LEFT_ARM(
            EasArmorStand.Part.LEFT_ARM,
            "Left arm",
            new Vector3d(5, 22, 0),
            new Vector3d(0, -10, 0),
            new Matrix3d().rotationX(-Math.PI / 2),
            Util.SWITCH_FORWARD_DOWN),
    RIGHT_ARM(
            EasArmorStand.Part.RIGHT_ARM,
            "Right arm",
            new Vector3d(-5, 22, 0),
            new Vector3d(0, -10, 0),
            new Matrix3d().rotationX(-Math.PI / 2),
            Util.SWITCH_FORWARD_DOWN),
    LEFT_LEG(
            EasArmorStand.Part.LEFT_LEG,
            "Left leg",
            new Vector3d(1.9, 12, 0),
            new Vector3d(0, -11, 0),
            new Matrix3d().rotationX(-Math.PI / 2),
            Util.SWITCH_FORWARD_DOWN),
    RIGHT_LEG(
            EasArmorStand.Part.RIGHT_LEG,
            "Right leg",
            new Vector3d(-1.9, 12, 0),
            new Vector3d(0, -11, 0),
            new Matrix3d().rotationX(-Math.PI / 2),
            Util.SWITCH_FORWARD_DOWN);

    private static final double SCALE = 1.0 / 16;
    private final EasArmorStand.Part part;
    private final String name;
    private final Vector3dc offset;
    private final Vector3dc length;
    private final Vector3dc smallOffset;
    private final Vector3dc smallLength;
    private final Matrix3dc rotation;
    private final Matrix3dc transform;

    BoneType(EasArmorStand.Part part, String name, Vector3d offset, Vector3d length, Matrix3d rotation, Matrix3d transform) {
        this.part = part;
        this.name = name;
        this.offset = offset.mul(SCALE, new Vector3d());
        this.length = length.mul(SCALE, new Vector3d());
        this.smallOffset = this.offset.mul(0.5, new Vector3d());
        this.smallLength = this.offset.mul(0.5, new Vector3d());
        this.rotation = rotation;
        this.transform = transform;
    }

    @Override
    public String toString() {
        return name;
    }

    public EasArmorStand.Part part() {
        return part;
    }

    public Vector3dc offset(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return offset;
        } else {
            return smallOffset;
        }
    }

    public Vector3dc length(EasArmorStand entity) {
        if (!entity.isSmall()) {
            return length;
        } else {
            return smallLength;
        }
    }

    public Matrix3dc rotation() {
        return rotation;
    }

    public Matrix3dc transform() {
        return transform;
    }
}
