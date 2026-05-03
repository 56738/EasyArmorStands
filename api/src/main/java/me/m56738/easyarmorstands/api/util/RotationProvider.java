package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;

public interface RotationProvider {
    @Contract(pure = true)
    static @NotNull RotationProvider of(@NotNull Quaterniondc rotation) {
        if (rotation.equals(identity().getRotation())) {
            return identity();
        }
        return new RotationProviderImpl(rotation);
    }

    @Contract(pure = true)
    static @NotNull RotationProvider identity() {
        return RotationProviderImpl.IDENTITY;
    }

    @Contract(pure = true)
    @NotNull Quaterniondc getRotation();
}
