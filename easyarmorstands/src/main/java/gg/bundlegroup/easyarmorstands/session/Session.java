package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.BoneType;
import gg.bundlegroup.easyarmorstands.Util;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class Session {
    private final SessionManager manager;
    private final EasPlayer player;
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;
    private final double range = 5;

    // Scratch space for calculations
    private final Matrix3d inverseEyeRotation = new Matrix3d();
    private final Matrix3d armorStandYaw = new Matrix3d();
    private final Matrix3d inverseArmorStandYaw = new Matrix3d();
    private final Vector3d boneAngle = new Vector3d();
    private final Matrix3d bonePose = new Matrix3d();
    private final Matrix3d boneRotation = new Matrix3d();
    private final Vector3d boneStart = new Vector3d();
    private final Vector3d boneEnd = new Vector3d();
    private final Matrix3d desiredBoneRotation = new Matrix3d();
    private final Vector3d desiredBoneEnd = new Vector3d();
    private final Vector3d desiredBoneDirection = new Vector3d();
    private final Vector3d desiredBoneUp = new Vector3d();
    private final Vector3d targetOffset = new Vector3d();

    // View-space values, stored across ticks while dragging
    private final Vector3d localBoneEnd = new Vector3d();
    private final Matrix3d localBoneRotation = new Matrix3d();

    private int rightClickTicks = 0;
    private BoneType boneType;

    public Session(SessionManager manager, EasPlayer player, EasArmorStand entity) {
        this.manager = manager;
        this.player = player;
        this.entity = entity;
        if (player.platform().canSetEntityGlowing() && player.platform().canHideEntities()) {
            this.skeleton = entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setPersistent(false);
                e.setGravity(false);
                Vector3d pose = new Vector3d();
                for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
                    e.setPose(part, entity.getPose(part, pose));
                }
                for (EasPlayer other : player.platform().getPlayers()) {
                    if (!player.equals(other)) {
                        other.hideEntity(e);
                    }
                }
            });
            skeleton.setGlowing(true);
        } else {
            this.skeleton = null;
        }
    }

    public void handleLeftClick() {
        if (rightClickTicks > 0) {
            handleHoldRightClick();
            rightClickTicks = 0;
            handleStopRightClick();
        }
    }

    public void handleRightClick() {
        if (rightClickTicks == 0) {
            handleStartRightClick();
        }
        rightClickTicks = 5;
    }

    public boolean update() {
        player.update();
        entity.update();

        if (rightClickTicks > 0) {
            handleHoldRightClick();
            rightClickTicks--;
            if (rightClickTicks == 0) {
                handleStopRightClick();
            }
        } else {
            updateView();
            updateArmorStand();
            updateTarget();
        }

        if (boneType != null) {
            player.sendActionBar(Component.text(boneType.toString()));
        } else {
            player.sendActionBar(Component.empty());
        }

        return player.isValid() && entity.isValid() && (skeleton == null || skeleton.isValid());
    }

    private void updateView() {
        player.getEyeRotation().transpose(inverseEyeRotation);
    }

    private void updateArmorStand() {
        armorStandYaw.rotationY(-Math.toRadians(entity.getYaw()));
        armorStandYaw.transpose(inverseArmorStandYaw);
    }

    private void updateBone(BoneType type) {
        boneType = type;
        if (type == null) {
            return;
        }
        armorStandYaw.transform(type.offset(entity), boneStart).add(entity.getPosition());
        armorStandYaw.mul(Util.fromEuler(entity.getPose(type.part(), boneAngle), bonePose), boneRotation);
        boneRotation.transform(type.length(entity), boneEnd).add(boneStart);
    }

    private void updateTarget() {
        BoneType bestType = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (BoneType type : BoneType.values()) {
            updateBone(type);
            inverseEyeRotation.transform(boneEnd.sub(player.getEyePosition(), targetOffset));
            double distance = targetOffset.z;
            // Eliminate forward part
            targetOffset.z = 0;
            // Distance from straight line
            double deviationSquared = targetOffset.lengthSquared();
            if (deviationSquared < 0.025) {
                if (distance > 0 && distance < bestDistance && distance < range) {
                    bestType = type;
                    bestDistance = distance;
                }
            }
        }

        updateBone(bestType);

        inverseEyeRotation.transform(boneEnd.sub(player.getEyePosition(), localBoneEnd));
        inverseEyeRotation.mul(boneRotation, localBoneRotation);
    }

    private void handleStartRightClick() {
        updateView();
        updateArmorStand();
        updateTarget();
    }

    private void handleHoldRightClick() {
        if (boneType == null) {
            return;
        }

        updateView();

        // Desired bone properties in world space
        player.getEyeRotation().transform(localBoneEnd, desiredBoneEnd).add(player.getEyePosition());
        desiredBoneEnd.sub(boneStart, desiredBoneDirection);
        player.getEyeRotation().mul(localBoneRotation, desiredBoneRotation);
        desiredBoneRotation.transform(boneType.transform().transform(Util.DOWN, desiredBoneUp));
        desiredBoneDirection.mul(-1);
        desiredBoneRotation.setLookAlong(desiredBoneDirection, desiredBoneUp).transpose();
        desiredBoneRotation.mul(boneType.transform());
        inverseArmorStandYaw.mul(desiredBoneRotation, bonePose);
        entity.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
        if (skeleton != null) skeleton.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
        inverseEyeRotation.mul(desiredBoneRotation, localBoneRotation);
    }

    private void handleStopRightClick() {
    }

    public void stop() {
        if (rightClickTicks > 0) {
            handleHoldRightClick();
            rightClickTicks = 0;
            handleStopRightClick();
        }
        if (skeleton != null) skeleton.remove();
        player.sendActionBar(Component.empty());
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public EasArmorStand getSkeleton() {
        return skeleton;
    }
}
