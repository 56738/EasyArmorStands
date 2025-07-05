package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface PositionProvider {
    @Contract(pure = true)
    static @NotNull PositionProvider of(@NotNull Vector3dc position) {
        return new PositionProviderImpl(position);
    }

    @Contract(pure = true)
    @NotNull Vector3dc getPosition();
}
