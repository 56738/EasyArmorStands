package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import org.jetbrains.annotations.NotNull;

class RotationProviderImpl implements RotationProvider {
    static final RotationProviderImpl IDENTITY = new RotationProviderImpl(new Quaterniond());

    private final Quaterniondc rotation;

    RotationProviderImpl(Quaterniondc rotation) {
        this.rotation = new Quaterniond(rotation);
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotation;
    }
}
