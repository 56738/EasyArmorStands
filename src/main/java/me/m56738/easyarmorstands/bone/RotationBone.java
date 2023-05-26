package me.m56738.easyarmorstands.bone;

import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface RotationBone extends Bone, RotationProvider {
    Vector3dc getAnchor();

    void setRotation(Quaterniondc rotation);
}
