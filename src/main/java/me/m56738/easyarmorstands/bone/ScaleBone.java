package me.m56738.easyarmorstands.bone;

import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface ScaleBone extends Bone {
    Vector3dc getOrigin();

    Quaterniondc getRotation();

    Vector3dc getScale();

    void setScale(Vector3dc scale);
}
