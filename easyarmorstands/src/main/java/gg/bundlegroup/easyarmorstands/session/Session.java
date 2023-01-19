package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.BoneType;
import gg.bundlegroup.easyarmorstands.Util;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class Session {
    private static final double RANGE = 5;
    private final EasPlayer player;
    private final Cursor cursor;
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;

    // Scratch space for calculations
    private final Matrix3d armorStandYaw = new Matrix3d();
    private final Vector3d boneAngle = new Vector3d();
    private final Matrix3d bonePose = new Matrix3d();
    private final Matrix3d boneRotation = new Matrix3d();
    private final Vector3d boneStart = new Vector3d();
    private final Vector3d boneEnd = new Vector3d();
    private final Vector3d targetOffset = new Vector3d();

    private int rightClickTicks = 0;
    private BoneType boneType;

    public Session(EasPlayer player, EasArmorStand entity) {
        this.player = player;
        this.cursor = new Cursor(player);
        this.entity = entity;
        if (player.platform().canSetEntityGlowing() && player.platform().canHideEntities()) {
            this.skeleton = entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setPersistent(false);
                e.setGravity(false);
                e.setSmall(entity.isSmall());
                Vector3d pose = new Vector3d();
                for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
                    e.setPose(part, entity.getPose(part, pose));
                }
                for (EasPlayer other : player.platform().getPlayers()) {
                    if (!player.equals(other)) {
                        other.hideEntity(e);
                    }
                }
                e.setGlowing(true);
            });
        } else {
            this.skeleton = null;
        }
    }

    public void handleLeftClick() {
        if (rightClickTicks > 0) {
            player.update();
            cursor.lookAt(player.getEyePosition());
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
            updateArmorStand();
            updateTarget();
        }

        if (boneType != null) {
            TextColor color = rightClickTicks > 0 ? NamedTextColor.YELLOW : NamedTextColor.GRAY;
            player.sendActionBar(Component.text(boneType.toString(), color));
        } else {
            player.sendActionBar(Component.empty());
        }

        return player.isValid() && entity.isValid() && (skeleton == null || skeleton.isValid());
    }

    private void updateArmorStand() {
        armorStandYaw.rotationY(-Math.toRadians(entity.getYaw()));
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
            boneEnd.sub(player.getEyePosition(), targetOffset).mulTranspose(player.getEyeRotation());
            double distance = targetOffset.z;
            // Eliminate forward part
            targetOffset.z = 0;
            // Distance from straight line
            double deviationSquared = targetOffset.lengthSquared();
            if (deviationSquared < 0.025) {
                if (distance > 0 && distance < bestDistance && distance < RANGE) {
                    bestType = type;
                    bestDistance = distance;
                }
            }
        }

        updateBone(bestType);
    }

    private void handleStartRightClick() {
        updateArmorStand();
        updateTarget();
        cursor.start(boneStart, boneEnd, boneRotation);
    }

    private void handleHoldRightClick() {
        if (boneType == null) {
            return;
        }

        // Desired bone properties in world space
        bonePose.setTransposed(armorStandYaw).mul(cursor.update());
        entity.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
        if (skeleton != null) skeleton.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
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

    public BoneType getBoneType() {
        return boneType;
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public EasArmorStand getSkeleton() {
        return skeleton;
    }
}
