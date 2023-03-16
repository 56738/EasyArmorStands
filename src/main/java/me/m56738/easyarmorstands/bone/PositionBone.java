package me.m56738.easyarmorstands.bone;

import org.joml.Vector3dc;

public interface PositionBone extends Bone {
    Vector3dc getPosition();

    void setPosition(Vector3dc position);
}
