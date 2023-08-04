package me.m56738.easyarmorstands.particle;

import org.joml.Vector3dc;

public interface CircleParticle extends ColoredParticle {
    Vector3dc getCenter();

    void setCenter(Vector3dc center);

    Vector3dc getAxis();

    void setAxis(Vector3dc axis);

    double getRadius();

    void setRadius(double radius);
}
