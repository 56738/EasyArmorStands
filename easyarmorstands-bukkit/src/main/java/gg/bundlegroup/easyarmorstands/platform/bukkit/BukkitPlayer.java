package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
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

public class BukkitPlayer extends BukkitEntity<Player> implements EasPlayer, ForwardingAudience.Single {
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
        double distance = from.distance(to);
        int parts = (int) Math.round(distance * 5);
        if (parts > 100) {
            parts = 100;
        }
        int min = includeEnds ? 0 : 1;
        int max = includeEnds ? parts : parts - 1;
        for (int i = min; i <= max; i++) {
            double t = i / (double) parts;
            particleSpawner.spawnParticle(get(),
                    from.x() + t * (to.x() - from.x()),
                    from.y() + t * (to.y() - from.y()),
                    from.z() + t * (to.z() - from.z()),
                    options);
        }
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
