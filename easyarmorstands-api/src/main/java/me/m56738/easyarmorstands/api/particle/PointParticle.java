package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface PointParticle extends ColoredParticle {
    double getSize();

    void setSize(double size);

    @NotNull Vector3dc getPosition();

    void setPosition(@NotNull Vector3dc position);

    @NotNull Quaterniondc getRotation();

    void setRotation(@NotNull Quaterniondc rotation);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
