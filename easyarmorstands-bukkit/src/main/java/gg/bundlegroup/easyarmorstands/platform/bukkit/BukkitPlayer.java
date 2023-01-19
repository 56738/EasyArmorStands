package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitPlayer extends BukkitEntity<Player> implements EasPlayer, ForwardingAudience.Single {
    private final Audience audience;
    private final EntityHider entityHider;
    private final Vector3d eyePosition = new Vector3d();
    private final Matrix3d eyeRotation = new Matrix3d();

    public BukkitPlayer(BukkitPlatform platform, Player player, Audience audience) {
        super(platform, player);
        this.audience = audience;
        this.entityHider = platform.entityHider();
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
    public boolean hasPermission(String permission) {
        return get().hasPermission(permission);
    }

    @Override
    public @NotNull Audience audience() {
        return audience;
    }
}
