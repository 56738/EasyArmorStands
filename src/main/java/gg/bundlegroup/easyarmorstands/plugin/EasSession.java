package gg.bundlegroup.easyarmorstands.plugin;

import gg.bundlegroup.easyarmorstands.api.Session;
import gg.bundlegroup.easyarmorstands.api.event.PlayerEditArmorStandPoseEvent;
import gg.bundlegroup.easyarmorstands.api.event.PlayerStartArmorStandEditorEvent;
import gg.bundlegroup.easyarmorstands.api.event.PlayerStopArmorStandEditorEvent;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class EasSession implements Session {
    private final SessionManager manager;
    private final Player player;
    private final Audience audience;
    private final ArmorStand entity;

    // Scratch space for calculations
    private final Vector3d eyes = new Vector3d();
    private final Matrix3d view = new Matrix3d();
    private final Matrix3d inverseView = new Matrix3d();
    private final Vector3d armorStandPos = new Vector3d();
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
    private final Vector3d rightClickDistance = new Vector3d();

    // View-space values, stored across ticks while dragging
    private final Vector3d localBoneEnd = new Vector3d();
    private final Matrix3d localBoneRotation = new Matrix3d();

    private int rightClickTicks = 0;
    private EasBoneType rightClickBoneType;
    private BukkitTask task;
    private boolean running;

    public EasSession(SessionManager manager, Player player, Audience audience, ArmorStand entity) {
        this.manager = manager;
        this.player = player;
        this.audience = audience;
        this.entity = entity;
    }

    @Override
    public Player player() {
        return player;
    }

    public Audience audience() {
        return audience;
    }

    @Override
    public ArmorStand entity() {
        return entity;
    }

    @Override
    public boolean running() {
        return running;
    }

    public void handleRightClick() {
        if (rightClickTicks == 0) {
            handleStartRightClick();
        }
        rightClickTicks = 5;
    }

    public boolean update() {
        if (rightClickTicks > 0) {
            handleHoldRightClick();
            rightClickTicks--;
            if (rightClickTicks == 0) {
                handleStopRightClick();
            }
        }

        return true;
    }

    private void updateView() {
        Location location = player.getEyeLocation();
        eyes.set(location.getX(), location.getY(), location.getZ());
        view.rotationZYX(
                0,
                -Math.toRadians(location.getYaw()),
                Math.toRadians(location.getPitch()));
        view.transpose(inverseView);
    }

    private void updateArmorStand() {
        Location location = entity.getLocation();
        armorStandPos.set(location.getX(), location.getY(), location.getZ());
        armorStandYaw.rotationY(-Math.toRadians(location.getYaw()));
        armorStandYaw.transpose(inverseArmorStandYaw);
    }

    private void updateBone(EasBoneType type) {
        armorStandYaw.transform(type.offset(entity), boneStart).add(armorStandPos);
        Util.fromEuler(type.getPose(entity), bonePose);
        armorStandYaw.mul(bonePose, boneRotation);
        boneRotation.transform(type.length(entity), boneEnd).add(boneStart);
    }

    private void handleStartRightClick() {
        updateView();
        updateArmorStand();

        // Find which bone we clicked
        EasBoneType bestType = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (EasBoneType type : EasBoneType.values()) {
            updateBone(type);
            inverseView.transform(boneEnd.sub(eyes, rightClickDistance));
            double distance = rightClickDistance.z;
            // Eliminate forward part
            rightClickDistance.z = 0;
            // Distance from straight line
            double deviationSquared = rightClickDistance.lengthSquared();
            if (deviationSquared < 0.025) {
                if (distance > 0 && distance < bestDistance) {
                    bestType = type;
                    bestDistance = distance;
                }
            }
        }

        if (bestDistance > 5) {
            // Too far away or didn't click any bone end, abort
            rightClickBoneType = null;
            return;
        }

        // Bone properties relative to the view matrix
        updateBone(bestType);
        rightClickBoneType = bestType;
        inverseView.transform(boneEnd.sub(eyes, localBoneEnd));
        inverseView.mul(boneRotation, localBoneRotation);
    }

    private void handleHoldRightClick() {
        if (rightClickBoneType == null) {
            return;
        }

        updateView();

        // Desired bone properties in world space
        view.transform(localBoneEnd, desiredBoneEnd).add(eyes);
        desiredBoneEnd.sub(boneStart, desiredBoneDirection);
        view.mul(localBoneRotation, desiredBoneRotation);
        desiredBoneRotation.transform(rightClickBoneType.transform().transform(Util.DOWN, desiredBoneUp));
        desiredBoneDirection.mul(-1);
        desiredBoneRotation.setLookAlong(desiredBoneDirection, desiredBoneUp).transpose();
        desiredBoneRotation.mul(rightClickBoneType.transform());
        inverseArmorStandYaw.mul(desiredBoneRotation, bonePose);
        EulerAngle angle = Util.toEuler(bonePose, boneAngle);

        PlayerEditArmorStandPoseEvent event = new PlayerEditArmorStandPoseEvent(this, rightClickBoneType.boneType(), angle);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        rightClickBoneType.setPose(entity, angle);
        inverseView.mul(desiredBoneRotation, localBoneRotation);
    }

    private void handleStopRightClick() {
        rightClickBoneType = null;
    }

    @Override
    public void start() {
        if (running) {
            return;
        }

        running = true;
        PlayerStartArmorStandEditorEvent event = new PlayerStartArmorStandEditorEvent(this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            running = false;
            return;
        }

        manager.registerSession(this);
        task = new SessionTask(this).runTaskTimer(manager.plugin(), 0, 1);
    }

    @Override
    public void stop() {
        if (!running) {
            return;
        }
        running = false;

        manager.unregisterSession(this);

        if (task != null) {
            task.cancel();
            task = null;
        }

        if (rightClickTicks > 0) {
            handleHoldRightClick();
            rightClickTicks = 0;
            handleStopRightClick();
        }

        PlayerStopArmorStandEditorEvent event = new PlayerStopArmorStandEditorEvent(this);
        Bukkit.getPluginManager().callEvent(event);
    }
}
