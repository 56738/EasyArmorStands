package gg.bundlegroup.easyarmorstands.plugin;

import gg.bundlegroup.easyarmorstands.api.BoneType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public enum EasBoneType {
    HEAD(BoneType.HEAD, new Vector3d(0, 23, 0), new Vector3d(0, 7, 0), new Matrix3d().rotationX(Math.PI / 2), Util.SWITCH_FORWARD_UP, ArmorStand::getHeadPose, ArmorStand::setHeadPose),
    BODY(BoneType.BODY, new Vector3d(0, 24, 0), new Vector3d(0, -12, 0), new Matrix3d().rotationX(-Math.PI / 2), Util.SWITCH_FORWARD_DOWN, ArmorStand::getBodyPose, ArmorStand::setBodyPose),
    LEFT_ARM(BoneType.LEFT_ARM, new Vector3d(5, 22, 0), new Vector3d(0, -10, 0), new Matrix3d().rotationX(-Math.PI / 2), Util.SWITCH_FORWARD_DOWN, ArmorStand::getLeftArmPose, ArmorStand::setLeftArmPose),
    RIGHT_ARM(BoneType.RIGHT_ARM, new Vector3d(-5, 22, 0), new Vector3d(0, -10, 0), new Matrix3d().rotationX(-Math.PI / 2), Util.SWITCH_FORWARD_DOWN, ArmorStand::getRightArmPose, ArmorStand::setRightArmPose),
    LEFT_LEG(BoneType.LEFT_LEG, new Vector3d(1.9, 12, 0), new Vector3d(0, -11, 0), new Matrix3d().rotationX(-Math.PI / 2), Util.SWITCH_FORWARD_DOWN, ArmorStand::getLeftLegPose, ArmorStand::setLeftLegPose),
    RIGHT_LEG(BoneType.RIGHT_LEG, new Vector3d(-1.9, 12, 0), new Vector3d(0, -11, 0), new Matrix3d().rotationX(-Math.PI / 2), Util.SWITCH_FORWARD_DOWN, ArmorStand::getRightLegPose, ArmorStand::setRightLegPose);

    private static final double SCALE = 1.0 / 16;
    private final BoneType boneType;
    private final Vector3dc offset;
    private final Vector3dc length;
    private final Vector3dc smallOffset;
    private final Vector3dc smallLength;
    private final Matrix3dc rotation;
    private final Matrix3dc transform;
    private final PoseGetter poseGetter;
    private final PoseSetter poseSetter;

    EasBoneType(BoneType boneType, Vector3d offset, Vector3d length, Matrix3d rotation, Matrix3d transform, PoseGetter poseGetter, PoseSetter poseSetter) {
        this.boneType = boneType;
        this.offset = offset.mul(SCALE, new Vector3d());
        this.length = length.mul(SCALE, new Vector3d());
        this.smallOffset = this.offset.mul(0.5, new Vector3d());
        this.smallLength = this.offset.mul(0.5, new Vector3d());
        this.rotation = rotation;
        this.transform = transform;
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
    }

    public BoneType boneType() {
        return boneType;
    }

    public Vector3dc offset(ArmorStand entity) {
        if (!entity.isSmall()) {
            return offset;
        } else {
            return smallOffset;
        }
    }

    public Vector3dc length(ArmorStand entity) {
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

    public EulerAngle getPose(ArmorStand entity) {
        return poseGetter.getPose(entity);
    }

    public void setPose(ArmorStand entity, EulerAngle pose) {
        poseSetter.setPose(entity, pose);
    }

    private interface PoseGetter {
        EulerAngle getPose(ArmorStand entity);
    }

    private interface PoseSetter {
        void setPose(ArmorStand entity, EulerAngle pose);
    }
}
