package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface AxisAlignedBoxParticle extends ColoredParticle {
    @NotNull Vector3dc getCenter();

    void setCenter(@NotNull Vector3dc center);

    @NotNull Vector3dc getSize();

    void setSize(@NotNull Vector3dc size);

    double getLineWidth();

    void setLineWidth(double lineWidth);
}
