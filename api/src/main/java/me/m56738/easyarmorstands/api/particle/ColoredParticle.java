package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface ColoredParticle extends Particle {
    @NotNull ParticleColor getColor();

    void setColor(@NotNull ParticleColor color);
}
