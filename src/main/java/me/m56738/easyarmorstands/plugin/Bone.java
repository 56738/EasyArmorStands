package me.m56738.easyarmorstands.plugin;

import me.m56738.easyarmorstands.math.Matrix3x3;
import me.m56738.easyarmorstands.math.Vector3;

public final class Bone {
    private final Matrix3x3 rotation;
    private final Vector3 start;
    private final Vector3 end;

    public Bone(Matrix3x3 rotation, Vector3 start, Vector3 end) {
        this.rotation = rotation;
        this.start = start;
        this.end = end;
    }

    public Matrix3x3 rotation() {
        return rotation;
    }

    public Vector3 start() {
        return start;
    }

    public Vector3 end() {
        return end;
    }
}
