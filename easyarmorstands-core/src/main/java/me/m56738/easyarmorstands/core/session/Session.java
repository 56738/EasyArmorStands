package me.m56738.easyarmorstands.core.session;

import me.m56738.easyarmorstands.core.bone.Bone;
import me.m56738.easyarmorstands.core.platform.EasArmorEntity;
import me.m56738.easyarmorstands.core.platform.EasItem;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import me.m56738.easyarmorstands.core.tool.Tool;
import me.m56738.easyarmorstands.core.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
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
    private final Map<String, Bone> bones = new HashMap<>();

    private int clickTicks = 5;
    private Bone bone;
    private boolean active;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;

    public Session(EasPlayer player) {
        this.player = player;
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
            onLeftClick();
            return;
        }
        if (bone.onLeftClick()) {
            return;
        }
        active = false;
    }

    protected void onLeftClick() {
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
        player.update();

        if (clickTicks > 0) {
            clickTicks--;
        }

        if (active) {
            bone.refresh();
            bone.update();
        } else {
            updateTargetBone();
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

        return player.isValid() && isHoldingTool();
    }

    private boolean isHoldingTool() {
        EasItem mainHand = player.getItem(EasArmorEntity.Slot.MAIN_HAND);
        if (mainHand != null && mainHand.isTool()) {
            return true;
        }
        EasItem offHand = player.getItem(EasArmorEntity.Slot.OFF_HAND);
        return offHand != null && offHand.isTool();
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
        player.clearTitle();
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

    public void setBone(Bone bone, Tool tool, Vector3dc cursor) {
        setBone(bone);
        bone.select(tool, cursor);
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

    public void startMoving(Vector3dc cursor) {
        player.update();

        Bone bone = bones.get("position");
        if (bone != null) {
            Tool tool = bone.getTools().get("move");
            if (tool != null) {
                setBone(bone, tool, cursor != null ? cursor : tool.getTarget());
            } else {
                setBone(bone);
            }
        }
    }

    public Map<String, Bone> getBones() {
        return Collections.unmodifiableMap(bones);
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
