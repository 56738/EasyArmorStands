package gg.bundlegroup.easyarmorstands.core.session;

import gg.bundlegroup.easyarmorstands.core.bone.Bone;
import gg.bundlegroup.easyarmorstands.core.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasFeature;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.core.tool.Tool;
import gg.bundlegroup.easyarmorstands.core.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Session {
    public static final double DEFAULT_SNAP_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_ANGLE_SNAP_INCREMENT = 360.0 / 256;
    private final EasPlayer player;
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;
    private final Map<String, Bone> bones = new HashMap<>();
    private final Matrix3d armorStandYaw = new Matrix3d();

    private int clickTicks = 5;
    private Bone bone;
    private boolean active;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;

    public Session(EasPlayer player, EasArmorStand entity) {
        this.player = player;
        this.entity = entity;
        if (player.platform().hasFeature(EasFeature.ENTITY_GLOW)) {
            this.skeleton = entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setPersistent(false);
                e.setGravity(false);
                e.setCanTick(false);
                updateSkeleton(e);
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

    public void addBone(String name, Bone bone) {
        bones.put(name, bone);
    }

    private boolean handleClick() {
        if (clickTicks > 0) {
            return false;
        }
        clickTicks = 5;
        return true;
    }

    public void handleLeftClick() {
        if (!handleClick()) {
            return;
        }
        if (!active) {
            openMenu();
            return;
        }
        if (bone.onLeftClick()) {
            return;
        }
        active = false;
    }

    public void handleRightClick() {
        if (!handleClick()) {
            return;
        }
        update();
        if (bone != null) {
            if (active) {
                bone.onRightClick();
            } else {
                active = true;
                bone.start();
            }
        }
    }

    public double snap(double value) {
        return Util.snap(value, snapIncrement);
    }

    public double snapAngle(double value) {
        return Util.snap(value, angleSnapIncrement);
    }

    public boolean update() {
        if (clickTicks > 0) {
            clickTicks--;
        }

        player.update();
        entity.update();

        armorStandYaw.rotationY(-Math.toRadians(entity.getYaw()));

        if (active) {
            bone.refresh();
            bone.update();
        } else {
            updateTargetBone();
        }

        if (skeleton != null) {
            updateSkeleton(skeleton);
        }

        if (!active) {
            // No bone is active, display the name of the bone the player is looking at
            if (bone != null) {
                player.showTitle(Title.title(Component.empty(), bone.getName(),
                        Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO)));
            } else {
                player.clearTitle();
            }
        }

        return player.isValid() && entity.isValid() && (skeleton == null || skeleton.isValid()) &&
                player.getEyePosition().distanceSquared(entity.getPosition()) < 100 * 100;
    }

    private void updateTargetBone() {
        Bone bestBone = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        Vector3d temp = new Vector3d();
        for (Bone candidate : bones.values()) {
            candidate.refresh();
            candidate.getPosition().sub(player.getEyePosition(), temp).mulTranspose(player.getEyeRotation());
            double distance = temp.z;
            // Eliminate forward part
            temp.z = 0;
            // Distance from straight line
            double deviationSquared = temp.lengthSquared();
            double threshold = getLookThreshold();
            if (deviationSquared < threshold * threshold) {
                if (distance > 0 && distance < bestDistance && distance < getRange()) {
                    bestBone = candidate;
                    bestDistance = distance;
                }
            }
        }
        bone = bestBone;
        for (Bone candidate : bones.values()) {
            player.showPoint(candidate.getPosition(),
                    candidate == bestBone ? NamedTextColor.YELLOW : NamedTextColor.WHITE);
        }
    }

    public void stop() {
        if (skeleton != null) skeleton.remove();
        player.clearTitle();
    }

    private void updateSkeleton(EasArmorStand skeleton) {
        skeleton.teleport(entity.getPosition(), entity.getYaw(), 0);
        skeleton.setSmall(entity.isSmall());
        Vector3d pose = new Vector3d();
        for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
            skeleton.setPose(part, entity.getPose(part, pose));
        }
    }

    public void setBone(Bone bone) {
        if (bone != null) {
            this.bone = bone;
            this.active = true;
            bone.refresh();
            bone.start();
        } else {
            this.active = false;
        }
    }

    public void setBone(Bone bone, Tool tool) {
        setBone(bone);
        bone.select(tool);
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public EasPlayer getPlayer() {
        return player;
    }

    public double getRange() {
        return 10;
    }

    public double getLookThreshold() {
        return 0.15;
    }

    public boolean canMove(Vector3dc position) {
        return player.platform().canMoveSession(this, position);
    }

    public boolean move(Vector3dc position) {
        return move(position, entity.getYaw());
    }

    public boolean move(Vector3dc position, float yaw) {
        return player.platform().canMoveSession(this, position) && entity.teleport(position, yaw, 0);
    }

    public void startMoving() {
        player.update();
        entity.update();

        Bone bone = bones.get("position");
        if (bone != null) {
            Tool tool = bone.getTools().get("move");
            if (tool != null) {
                setBone(bone, tool);
            } else {
                setBone(bone);
            }
        }
    }

    public void openMenu() {
        SessionMenu inventory = new SessionMenu(this, player.platform());
        player.openInventory(inventory.getInventory());
    }

    public boolean isToolInOffHand() {
        EasItem item = player.getItem(EasArmorEntity.Slot.OFF_HAND);
        if (item == null) {
            return false;
        }
        return item.isTool();
    }

    public void hideSkeleton(EasPlayer player) {
        if (skeleton != null) {
            player.hideEntity(skeleton);
        }
    }

    public Map<String, Bone> getBones() {
        return Collections.unmodifiableMap(bones);
    }

    public Matrix3dc getArmorStandYaw() {
        return armorStandYaw;
    }

    public double getSnapIncrement() {
        return snapIncrement;
    }

    public void setSnapIncrement(double snapIncrement) {
        this.snapIncrement = snapIncrement;
    }

    public double getAngleSnapIncrement() {
        return angleSnapIncrement;
    }

    public void setAngleSnapIncrement(double angleSnapIncrement) {
        this.angleSnapIncrement = angleSnapIncrement;
    }
}
