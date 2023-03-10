package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.tool.Tool;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.RGBLike;
import net.kyori.adventure.util.Ticks;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Session implements ForwardingAudience.Single {
    public static final double DEFAULT_SNAP_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_ANGLE_SNAP_INCREMENT = 360.0 / 256;
    private final Player player;
    private final Audience audience;
    private final ParticleCapability particleCapability;
    private final Map<String, Bone> bones = new HashMap<>();

    private int clickTicks = 5;
    private Bone bone;
    private boolean active;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;

    public Session(Player player) {
        this.player = player;
        this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
        this.particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
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
                audience.showTitle(Title.title(Component.empty(), bone.getName(),
                        Title.Times.times(Duration.ZERO, Ticks.duration(20), Duration.ZERO)));
            } else {
                audience.clearTitle();
            }
        }

        return player.isValid() && isHoldingTool();
    }

    private boolean isHoldingTool() {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        EquipmentCapability equipmentCapability = plugin.getCapability(EquipmentCapability.class);
        EntityEquipment equipment = player.getEquipment();
        for (EquipmentSlot hand : equipmentCapability.getHands()) {
            ItemStack item = equipmentCapability.getItem(equipment, hand);
            if (Util.isTool(item)) {
                return true;
            }
        }
        return false;
    }

    private void updateTargetBone() {
        Bone bestBone = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        Vector3d temp = new Vector3d();
        for (Bone candidate : bones.values()) {
            candidate.refresh();
            Location eyeLocation = player.getEyeLocation();
            candidate.getPosition().sub(Util.toVector3d(eyeLocation), temp).mulTranspose(Util.getRotation(eyeLocation, new Matrix3d()));
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
            showPoint(candidate.getPosition(),
                    candidate == bestBone ? NamedTextColor.YELLOW : NamedTextColor.WHITE);
        }
    }

    public void stop() {
        audience.clearTitle();
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

    public Player getPlayer() {
        return player;
    }

    public double getRange() {
        return 10;
    }

    public double getLookThreshold() {
        return 0.15;
    }

    public void startMoving(Vector3dc cursor) {
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

    @Override
    public @NotNull Audience audience() {
        return audience;
    }

    private int getParticleCount(double length) {
        return (int) Math.round(length * 5);
    }

    public void showPoint(Vector3dc position, Color color) {
        particleCapability.spawnParticle(player, position.x(), position.y(), position.z(), color);
    }

    public void showPoint(Vector3dc position, RGBLike color) {
        showPoint(position, Util.toColor(color));
    }

    public void showLine(Vector3dc from, Vector3dc to, Color color, boolean includeEnds) {
        double length = from.distance(to);
        int count = getParticleCount(length);
        if (count > 100) {
            count = 100;
        }
        int min = includeEnds ? 0 : 1;
        int max = includeEnds ? count : count - 1;
        for (int i = min; i <= max; i++) {
            double t = i / (double) count;
            particleCapability.spawnParticle(player,
                    from.x() + t * (to.x() - from.x()),
                    from.y() + t * (to.y() - from.y()),
                    from.z() + t * (to.z() - from.z()),
                    color);
        }
    }

    public void showLine(Vector3dc start, Vector3dc end, RGBLike color, boolean includeEnds) {
        showLine(start, end, Util.toColor(color), includeEnds);
    }

    public void showCircle(Vector3dc center, Vector3dc axis, Color color, double radius) {
        double circumference = 2 * Math.PI * radius;
        int count = getParticleCount(circumference);
        if (count > 100) {
            count = 100;
        }
        Vector3d offset = center.cross(axis, new Vector3d()).normalize(radius);
        double axisX = axis.x();
        double axisY = axis.y();
        double axisZ = axis.z();
        double angle = 2 * Math.PI / count;
        for (int i = 0; i < count; i++) {
            offset.rotateAxis(angle, axisX, axisY, axisZ);
            particleCapability.spawnParticle(player,
                    center.x() + offset.x,
                    center.y() + offset.y,
                    center.z() + offset.z,
                    color);
        }
    }

    public void showCircle(Vector3dc center, Vector3dc axis, RGBLike color, double radius) {
        showCircle(center, axis, Util.toColor(color), radius);
    }
}
