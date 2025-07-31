package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface PositionProvider {
    @Contract(pure = true)
    static @NotNull PositionProvider of(@NotNull Vector3dc position) {
        return new StaticPositionProvider(new Vector3d(position));
    }

    @Contract(pure = true)
    static @NotNull PositionProvider of(@NotNull Property<@NotNull Location> property) {
        return new LocationPropertyPositionProvider(property);
    }

    @Contract(pure = true)
    @NotNull Vector3dc position();
}
