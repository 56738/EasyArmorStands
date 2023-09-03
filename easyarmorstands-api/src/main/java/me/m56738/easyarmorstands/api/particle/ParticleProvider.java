package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface ParticleProvider {
    @Contract(value = "-> new", pure = true)
    @NotNull PointParticle createPoint();

    @Contract(value = "-> new", pure = true)
    @NotNull LineParticle createLine();

    @Contract(value = "-> new", pure = true)
    @NotNull CircleParticle createCircle();

    @Contract(value = "-> new", pure = true)
    @NotNull BoundingBoxParticle createAxisAlignedBox();
}
