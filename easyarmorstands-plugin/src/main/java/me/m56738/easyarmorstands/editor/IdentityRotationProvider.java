package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.util.Util;
import org.joml.Quaterniondc;

class IdentityRotationProvider implements RotationProvider {
    static final IdentityRotationProvider INSTANCE = new IdentityRotationProvider();

    private IdentityRotationProvider() {
    }

    @Override
    public Quaterniondc getRotation() {
        return Util.IDENTITY;
    }
}
