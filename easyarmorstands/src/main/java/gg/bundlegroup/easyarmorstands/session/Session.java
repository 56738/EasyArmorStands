package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.BoneType;
import gg.bundlegroup.easyarmorstands.Util;
import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;

import java.awt.*;
import java.time.Duration;

public class Session {
    private static final double RANGE = 5;
    private final EasPlayer player;
    private final Cursor cursor;
    private final AimManipulator aimManipulator;
    private final AxisManipulator axisManipulator;
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;

    // Scratch space for calculations
    private final Matrix3d armorStandYaw = new Matrix3d();
    private final Vector3d boneAngle = new Vector3d();
    private final Matrix3d bonePose = new Matrix3d();
    private final Matrix3d boneRotation = new Matrix3d();
    private final Vector3d boneStart = new Vector3d();
    private final Vector3d boneEnd = new Vector3d();
    private final Vector3d temp = new Vector3d();

    private Mode mode = Mode.NONE;
    private int rightClickTicks;
    private BoneType boneType;

    public Session(EasPlayer player, EasArmorStand entity) {
        this.player = player;
        this.cursor = new Cursor(player);
        this.aimManipulator = new AimManipulator(player);
        this.axisManipulator = new AxisManipulator(player);
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
        mode = Mode.NONE;
    }

    public void handleRightClick() {
        if (rightClickTicks > 0) {
            return;
        }
        rightClickTicks = 5;
        update();
        if (boneType == null) {
            return;
        }
        updateBone(boneType);
        switch (mode) {
            case NONE:
                cursor.start(boneEnd, false);
                aimManipulator.start(cursor.get(), boneStart, boneRotation);
                mode = Mode.AIM;
                break;
            case AIM:
                axisManipulator.start(cursor.get(), boneStart, new Vector3d(1, 0, 0), Color.RED, boneRotation);
                mode = Mode.X;
                break;
            case X:
                axisManipulator.start(cursor.get(), boneStart, new Vector3d(0, 0, 1), Color.BLUE, boneRotation);
                mode = Mode.Z;
                break;
            case Z:
                axisManipulator.start(cursor.get(), boneStart, new Vector3d(0, 1, 0), Color.GREEN, boneRotation);
                mode = Mode.Y;
                break;
            case Y:
                mode = Mode.NONE;
                break;
        }
    }

    public boolean update() {
        if (rightClickTicks > 0) {
            rightClickTicks--;
        }

        player.update();
        entity.update();
        updateArmorStand();

        Matrix3dc current = null;
        switch (mode) {
            case NONE:
                updateTarget();
                break;
            case AIM:
                current = aimManipulator.update(cursor.get());
                break;
            case X:
            case Y:
            case Z:
                current = axisManipulator.update(cursor.get());
                break;
        }
        if (current != null) {
            boneRotation.set(current);
            bonePose.setTransposed(armorStandYaw).mul(boneRotation);
            entity.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
            if (skeleton != null) skeleton.setPose(boneType.part(), Util.toEuler(bonePose, boneAngle));
        }

        if (boneType != null) {
            player.showTitle(Title.title(
                    mode.component,
                    Component.text(boneType.toString()),
                    Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO)
            ));
        } else {
            player.clearTitle();
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
            boneEnd.sub(player.getEyePosition(), temp).mulTranspose(player.getEyeRotation());
            double distance = temp.z;
            // Eliminate forward part
            temp.z = 0;
            // Distance from straight line
            double deviationSquared = temp.lengthSquared();
            if (deviationSquared < 0.025) {
                if (distance > 0 && distance < bestDistance && distance < RANGE) {
                    bestType = type;
                    bestDistance = distance;
                }
            }
            player.showPoint(boneEnd, Color.WHITE);
        }

        updateBone(bestType);
    }

    public void stop() {
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

    private enum Mode {
        NONE(Component.empty()),
        AIM(Component.text("Aim", NamedTextColor.YELLOW)),
        X(Component.text("X", NamedTextColor.RED)),
        Y(Component.text("Y", NamedTextColor.GREEN)),
        Z(Component.text("Z", NamedTextColor.BLUE));

        private final Component component;

        Mode(Component component) {
            this.component = component;
        }
    }
}
