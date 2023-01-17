package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityHider;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitPlayer extends BukkitEntity implements EasPlayer, ForwardingAudience.Single {
    private final Player player;
    private final Audience audience;
    private final EntityHider entityHider;
    private final Vector3d eyePosition = new Vector3d();
    private final Matrix3d eyeRotation = new Matrix3d();

    public BukkitPlayer(BukkitPlatform platform, Player player, Audience audience) {
        super(platform, player);
        this.player = player;
        this.audience = audience;
        this.entityHider = platform.entityHider();
    }

    @Override
    public void update() {
        super.update();
        Location location = player.getEyeLocation();
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
    }

    @Override
    public void showEntity(EasEntity entity) {
    }

    @Override
    public @NotNull Audience audience() {
        return audience;
    }
}
