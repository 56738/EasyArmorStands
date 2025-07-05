package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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
