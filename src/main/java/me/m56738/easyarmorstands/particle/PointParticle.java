package me.m56738.easyarmorstands.particle;

import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface PointParticle extends ColoredParticle {
    double getSize();

    void setSize(double size);

    Vector3dc getPosition();

    void setPosition(Vector3dc position);

    Quaterniondc getRotation();

    void setRotation(Quaterniondc rotation);

    boolean isBillboard();

    void setBillboard(boolean billboard);
}
