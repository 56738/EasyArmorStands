package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface CircleParticle extends ColoredParticle {
    Vector3dc getCenter();

    void setCenter(Vector3dc center);

    Axis getAxis();

    void setAxis(Axis axis);

    double getWidth();

    void setWidth(double width);

    Quaterniondc getRotation();

    void setRotation(Quaterniondc rotation);

    double getRadius();

    void setRadius(double radius);
}
