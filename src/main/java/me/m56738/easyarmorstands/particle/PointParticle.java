package me.m56738.easyarmorstands.particle;

import org.joml.Vector3dc;

public interface PointParticle extends ColoredParticle {
    Vector3dc getPosition();

    void setPosition(Vector3dc position);
}
