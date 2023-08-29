package me.m56738.easyarmorstands.editor;

import org.joml.Quaterniondc;

public interface RotationProvider {
    static RotationProvider identity() {
        return IdentityRotationProvider.INSTANCE;
    }

    Quaterniondc getRotation();
}
