package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface BoundingBoxParticle extends ColoredParticle {
    @NotNull BoundingBox getBoundingBox();

    void setBoundingBox(@NotNull BoundingBox box);

    double getLineWidth();

    void setLineWidth(double lineWidth);
}
