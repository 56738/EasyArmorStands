package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface ParticleProvider {
    @Contract(value = "-> new", pure = true)
    PointParticle createPoint();

    @Contract(value = "-> new", pure = true)
    LineParticle createLine();

    @Contract(value = "-> new", pure = true)
    CircleParticle createCircle();

    @Contract(value = "-> new", pure = true)
    AxisAlignedBoxParticle createAxisAlignedBox();
}
