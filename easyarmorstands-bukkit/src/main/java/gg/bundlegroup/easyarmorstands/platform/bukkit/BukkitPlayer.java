package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.ParticleSpawner;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitPlayer extends BukkitArmorEntity<Player> implements EasPlayer, ForwardingAudience.Single {
    private final Audience audience;
    private final EntityHider entityHider;
    private final ParticleSpawner particleSpawner;
    private final Vector3d eyePosition = new Vector3d();
    private final Matrix3d eyeRotation = new Matrix3d();

    public BukkitPlayer(BukkitPlatform platform, Player player, Audience audience) {
        super(platform, player);
        this.audience = audience;
        this.entityHider = platform.entityHider();
        this.particleSpawner = platform.particleSpawner();
    }

    @Override
    public void update() {
        super.update();
        Location location = get().getEyeLocation();
        eyePosition.set(location.getX(), location.getY(), location.getZ());
        eyeRotation.rotationZYX(
                0,
                -Math.toRadians(location.getYaw()),
                Math.toRadians(location.getPitch()));
    }

    @Override
    public Vector3dc getEyePosition() {
        return eyePosition;
    }

    @Override
    public Matrix3dc getEyeRotation() {
        return eyeRotation;
    }

    @Override
    public void hideEntity(EasEntity entity) {
        if (entityHider != null) {
            entityHider.hideEntity(platform().plugin(), get(), ((BukkitEntity<?>) entity).get());
        }
    }

    @Override
    public void showEntity(EasEntity entity) {
        if (entityHider != null) {
            entityHider.showEntity(platform().plugin(), get(), ((BukkitEntity<?>) entity).get());
        }
    }

    @Override
    public boolean isSneaking() {
        return get().isSneaking();
    }

    @Override
    public void giveTool() {
        get().getInventory().addItem(platform().toolChecker().createTool());
    }

    @Override
    public void lookAt(Vector3dc target) {
        Location location = get().getLocation();
        location.setDirection(new Vector(target.x(), target.y(), target.z()).subtract(get().getEyeLocation().toVector()));
        get().teleport(location);
        update();
    }

    private int getParticleCount(double length) {
        return (int) Math.round(length * 5);
    }

    @Override
    public void showPoint(Vector3dc point, RGBLike color) {
        if (particleSpawner == null) {
            return;
        }
        particleSpawner.spawnParticle(get(), point.x(), point.y(), point.z(), particleSpawner.getData(color));
    }

    @Override
    public void showLine(Vector3dc from, Vector3dc to, RGBLike color, boolean includeEnds) {
        if (particleSpawner == null) {
            return;
        }
        Object options = particleSpawner.getData(color);
        double length = from.distance(to);
        int count = getParticleCount(length);
        if (count > 100) {
            count = 100;
        }
        int min = includeEnds ? 0 : 1;
        int max = includeEnds ? count : count - 1;
        for (int i = min; i <= max; i++) {
            double t = i / (double) count;
            particleSpawner.spawnParticle(get(),
                    from.x() + t * (to.x() - from.x()),
                    from.y() + t * (to.y() - from.y()),
                    from.z() + t * (to.z() - from.z()),
                    options);
        }
    }

    @Override
    public void showCircle(Vector3dc center, Vector3dc axis, RGBLike color, double radius) {
        if (particleSpawner == null) {
            return;
        }
        Object options = particleSpawner.getData(color);
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
            particleSpawner.spawnParticle(get(),
                    center.x() + offset.x,
                    center.y() + offset.y,
                    center.z() + offset.z,
                    options);
        }
    }

    @Override
    public void openInventory(EasInventory inventory) {
        get().openInventory(((BukkitInventory) inventory).get());
    }

    @Override
    public boolean hasPermission(String permission) {
        return get().hasPermission(permission);
    }

    @Override
    public @NotNull Audience audience() {
        return audience;
    }
}
