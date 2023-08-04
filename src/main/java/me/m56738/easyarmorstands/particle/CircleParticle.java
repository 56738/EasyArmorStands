package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.util.Axis;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

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
