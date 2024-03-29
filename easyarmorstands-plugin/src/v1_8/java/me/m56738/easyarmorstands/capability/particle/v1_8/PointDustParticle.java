package me.m56738.easyarmorstands.capability.particle.v1_8;

import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.capability.particle.DustParticleCapability;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PointDustParticle extends DustParticle implements PointParticle {

    private final Vector3d position = new Vector3d();

    protected PointDustParticle(DustParticleCapability capability) {
        super(capability);
    }

    @Override
    public void update() {
        Color color = Color.fromRGB(this.color.red(), this.color.green(), this.color.blue());
        for (Player player : players) {
            capability.spawnParticle(player, position.x(), position.y(), position.z(), color);
        }
    }

    @Override
    public double getSize() {
        return 0;
    }

    @Override
    public void setSize(double size) {
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return position;
    }

    @Override
    public void setPosition(@NotNull Vector3dc position) {
        this.position.set(position);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public void setRotation(@NotNull Quaterniondc rotation) {
    }

    @Override
    public boolean isBillboard() {
        return true;
    }

    @Override
    public void setBillboard(boolean billboard) {
    }
}
