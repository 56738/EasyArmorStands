package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

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
