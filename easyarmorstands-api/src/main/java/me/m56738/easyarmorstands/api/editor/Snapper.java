package me.m56738.easyarmorstands.api.editor;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

@ApiStatus.NonExtendable
public interface Snapper {
    @Contract(pure = true)
    double snapOffset(double offset);

    default void snapOffset(@NotNull Vector3d offset) {
        offset.x = snapOffset(offset.x);
        offset.y = snapOffset(offset.y);
        offset.z = snapOffset(offset.z);
    }

    @Contract(pure = true)
    double snapPosition(double position);

    default void snapPosition(@NotNull Vector3d position) {
        position.x = snapPosition(position.x);
        position.y = snapPosition(position.y);
        position.z = snapPosition(position.z);
    }

    @Contract(pure = true)
    double snapAngle(double angle);
}
