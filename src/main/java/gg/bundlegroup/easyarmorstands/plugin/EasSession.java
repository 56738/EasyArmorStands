package gg.bundlegroup.easyarmorstands.plugin;

import gg.bundlegroup.easyarmorstands.api.Session;
import gg.bundlegroup.easyarmorstands.api.event.PlayerEditArmorStandPoseEvent;
import gg.bundlegroup.easyarmorstands.api.event.PlayerStartArmorStandEditorEvent;
import gg.bundlegroup.easyarmorstands.api.event.PlayerStopArmorStandEditorEvent;
import gg.bundlegroup.easyarmorstands.math.Matrix3x3;
import gg.bundlegroup.easyarmorstands.math.Vector3;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public class EasSession implements Session {
    private final SessionManager manager;
    private final Player player;
    private final Audience audience;
    private final ArmorStand entity;
    private int rightClickTicks = 0;
    private EasBoneType rightClickBoneType;
    private Vector3 rightClickBoneEnd;
    private Matrix3x3 rightClickBoneRotation;
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

    private Vector3 eyes() {
        return new Vector3(player.getEyeLocation());
    }

    private Matrix3x3 view() {
        final Location location = player.getEyeLocation();
        return new Matrix3x3(Math.toRadians(location.getPitch()), Math.toRadians(location.getYaw()));
    }

    private void handleStartRightClick() {
        Vector3 eyes = eyes();
        Matrix3x3 view = view();
        Matrix3x3 inverseView = view.transpose();

        // Find which bone we clicked
        EasBoneType bestType = null;
        double bestDistance = Double.POSITIVE_INFINITY;
        for (EasBoneType type : EasBoneType.values()) {
            Vector3 direction = inverseView.multiply(ArmorStandModel.getBoneEnd(entity, type).subtract(eyes));
            // Eliminate forward part
            Vector3 delta = direction.multiply(new Vector3(1, 1, 0));
            // Distance from straight line
            double deviationSquared = delta.lengthSquared();
            if (deviationSquared < 0.025) {
                double distance = direction.z();
                if (distance > 0 && distance < bestDistance) {
                    bestType = type;
                    bestDistance = distance;
                }
            }
        }

        if (bestDistance > 5) {
            // Too far away or didn't click any bone end, abort
            rightClickBoneType = null;
            rightClickBoneEnd = null;
            rightClickBoneRotation = null;
            return;
        }

        // Bone properties relative to the view matrix
        Bone bone = ArmorStandModel.getBone(entity, bestType);
        rightClickBoneType = bestType;
        rightClickBoneEnd = inverseView.multiply(bone.end().subtract(eyes));
        rightClickBoneRotation = inverseView.multiply(bone.rotation());
    }

    private void handleHoldRightClick() {
        if (rightClickBoneEnd == null) {
            return;
        }

        Matrix3x3 view = view();

        // Desired bone properties in world space
        Matrix3x3 yaw = Matrix3x3.rotateY(Math.toRadians(entity.getLocation().getYaw()));
        Vector3 end = view.multiply(rightClickBoneEnd).add(eyes()).subtract(ArmorStandModel.getBonePosition(entity, rightClickBoneType));
        Matrix3x3 rotation = view.multiply(rightClickBoneRotation);
        Matrix3x3 transform = rightClickBoneType.transform();
        Matrix3x3 result = new Matrix3x3(end, rotation.multiply(transform.multiply(Vector3.DOWN))).multiply(transform);
        EulerAngle angle = yaw.transpose().multiply(result).getEulerAngle();

        PlayerEditArmorStandPoseEvent event = new PlayerEditArmorStandPoseEvent(this, rightClickBoneType.boneType(), result, angle);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        rightClickBoneType.setPose(entity, angle);
        rightClickBoneRotation = view.transpose().multiply(result);
    }

    private void handleStopRightClick() {
        if (rightClickBoneEnd == null) {
            return;
        }

        rightClickBoneType = null;
        rightClickBoneEnd = null;
        rightClickBoneRotation = null;
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
