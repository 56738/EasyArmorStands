package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface ColoredParticle extends Particle {
    ParticleColor getColor();

    void setColor(ParticleColor color);
}
