package me.m56738.easyarmorstands.bone;

import org.joml.Matrix4dc;

public interface Bone {
    Matrix4dc getMatrix();

    boolean isValid();
}
