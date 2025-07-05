package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface CircleParticle extends ColoredParticle {
    @NotNull Vector3dc getCenter();

    void setCenter(@NotNull Vector3dc center);

    @NotNull Axis getAxis();

    void setAxis(@NotNull Axis axis);

    double getWidth();

    void setWidth(double width);

    @NotNull Quaterniondc getRotation();

    void setRotation(@NotNull Quaterniondc rotation);

    double getRadius();

    void setRadius(double radius);
}
