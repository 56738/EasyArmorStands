package gg.bundlegroup.easyarmorstands.plugin;

import gg.bundlegroup.easyarmorstands.api.BoneType;
import gg.bundlegroup.easyarmorstands.math.MathUtil;
import gg.bundlegroup.easyarmorstands.math.Matrix3x3;
import gg.bundlegroup.easyarmorstands.math.Vector3;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public enum EasBoneType {
    HEAD(BoneType.HEAD, new Vector3(0, 23, 0), new Vector3(0, 7, 0), Matrix3x3.rotateX(MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_UP, ArmorStand::getHeadPose, ArmorStand::setHeadPose),
    BODY(BoneType.BODY, new Vector3(0, 24, 0), new Vector3(0, -12, 0), Matrix3x3.rotateX(-MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_DOWN, ArmorStand::getBodyPose, ArmorStand::setBodyPose),
    LEFT_ARM(BoneType.LEFT_ARM, new Vector3(5, 22, 0), new Vector3(0, -10, 0), Matrix3x3.rotateX(-MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_DOWN, ArmorStand::getLeftArmPose, ArmorStand::setLeftArmPose),
    RIGHT_ARM(BoneType.RIGHT_ARM, new Vector3(-5, 22, 0), new Vector3(0, -10, 0), Matrix3x3.rotateX(-MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_DOWN, ArmorStand::getRightArmPose, ArmorStand::setRightArmPose),
    LEFT_LEG(BoneType.LEFT_LEG, new Vector3(1.9, 12, 0), new Vector3(0, -11, 0), Matrix3x3.rotateX(-MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_DOWN, ArmorStand::getLeftLegPose, ArmorStand::setLeftLegPose),
    RIGHT_LEG(BoneType.RIGHT_LEG, new Vector3(-1.9, 12, 0), new Vector3(0, -11, 0), Matrix3x3.rotateX(-MathUtil.R90), ArmorStandModel.SWITCH_FORWARD_DOWN, ArmorStand::getRightLegPose, ArmorStand::setRightLegPose);

    private final BoneType boneType;
    private final Vector3 offset;
    private final Vector3 length;
    private final Matrix3x3 rotation;
    private final Matrix3x3 transform;
    private final PoseGetter poseGetter;
    private final PoseSetter poseSetter;

    private static final double SCALE = 1.0 / 16;

    EasBoneType(BoneType boneType, Vector3 offset, Vector3 length, Matrix3x3 rotation, Matrix3x3 transform, PoseGetter poseGetter, PoseSetter poseSetter) {
        this.boneType = boneType;
        this.offset = offset.multiply(SCALE);
        this.length = length.multiply(SCALE);
        this.rotation = rotation;
        this.transform = transform;
        this.poseGetter = poseGetter;
        this.poseSetter = poseSetter;
    }

    public BoneType boneType() {
        return boneType;
    }

    public Vector3 offset(ArmorStand entity) {
        return offset.multiply(getScale(entity));
    }

    public Vector3 length(ArmorStand entity) {
        return length.multiply(getScale(entity));
    }

    public Matrix3x3 rotation() {
        return rotation;
    }

    public Matrix3x3 transform() {
        return transform;
    }

    public EulerAngle getPose(ArmorStand entity) {
        return poseGetter.getPose(entity);
    }

    public void setPose(ArmorStand entity, EulerAngle pose) {
        poseSetter.setPose(entity, pose);
    }

    private static double getScale(ArmorStand entity) {
        return entity.isSmall() ? 0.5 : 1;
    }

    private interface PoseGetter {
        EulerAngle getPose(ArmorStand entity);
    }

    private interface PoseSetter {
        void setPose(ArmorStand entity, EulerAngle pose);
    }
}
