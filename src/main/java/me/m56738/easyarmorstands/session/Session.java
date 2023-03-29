package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.node.Node;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.LinkedList;

public class Session implements ForwardingAudience.Single {
    public static final double DEFAULT_SNAP_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_ANGLE_SNAP_INCREMENT = 360.0 / 256;
    private final Player player;
    private final Audience audience;
    private final ParticleCapability particleCapability;
    protected final LinkedList<Node> nodeStack = new LinkedList<>();
    private int clickTicks = 5;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;

    public Session(Player player) {
        this.player = player;
        this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
        this.particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
    }

    public Node getNode() {
        return nodeStack.peek();
    }

    public void pushNode(Node node) {
        if (!nodeStack.isEmpty()) {
            nodeStack.peek().onExit();
        }
        nodeStack.push(node);
        node.onEnter();
    }

    public void replaceNode(Node node) {
        nodeStack.pop().onExit();
        nodeStack.push(node);
        node.onEnter();
    }

    public void popNode() {
        nodeStack.pop().onExit();
        if (!nodeStack.isEmpty()) {
            nodeStack.peek().onEnter();
        }
    }

    private boolean handleClick() {
        if (clickTicks > 0) {
            return false;
        }
        clickTicks = 5;
        return true;
    }

    private boolean handleClick(ClickType type) {
        Node node = nodeStack.peek();
        if (node == null) {
            return false;
        }
        Location eyeLocation = player.getEyeLocation();
        Vector3dc eyes = Util.toVector3d(eyeLocation);
        Vector3dc target = eyes.fma(getRange(), Util.toVector3d(eyeLocation.getDirection()), new Vector3d());
        return node.onClick(eyes, target, type);
    }

    public void handleLeftClick() {
        if (!handleClick()) {
            return;
        }
        if (!handleClick(ClickType.LEFT_CLICK)) {
            onLeftClick();
        }
    }

    protected void onLeftClick() {
    }

    public void handleRightClick() {
        if (!handleClick()) {
            return;
        }
        handleClick(ClickType.RIGHT_CLICK);
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

        Node node = nodeStack.peek();
        if (node != null) {
            Location eyeLocation = player.getEyeLocation();
            Vector3dc eyes = Util.toVector3d(eyeLocation);
            Vector3dc target = eyes.fma(getRange(), Util.toVector3d(eyeLocation.getDirection()), new Vector3d());
            node.onUpdate(eyes, target);
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

    public void stop() {
        commit();
        Node node = nodeStack.peek();
        if (node != null) {
            node.onExit();
        }
        audience.clearTitle();
    }

    public Entity getEntity() {
        return null;
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
        return (int) Math.round(length * particleCapability.getDensity());
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

    public void commit() {
    }

    public boolean isLookingAtPoint(Vector3dc eyes, Vector3dc target, Vector3dc position) {
        Vector3d closestOnEyeRay = Intersectiond.findClosestPointOnLineSegment(
                eyes.x(), eyes.y(), eyes.z(),
                target.x(), target.y(), target.z(),
                position.x(), position.y(), position.z(),
                new Vector3d());
        double threshold = getLookThreshold();
        return position.distanceSquared(closestOnEyeRay) < threshold * threshold;
    }
}
