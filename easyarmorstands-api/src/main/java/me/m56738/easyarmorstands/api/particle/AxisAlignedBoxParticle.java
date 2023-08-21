package me.m56738.easyarmorstands.api.particle;

import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface AxisAlignedBoxParticle extends ColoredParticle {
    Vector3dc getCenter();

    void setCenter(Vector3dc center);

    Vector3dc getSize();

    void setSize(Vector3dc size);

    double getLineWidth();

    void setLineWidth(double lineWidth);
}
