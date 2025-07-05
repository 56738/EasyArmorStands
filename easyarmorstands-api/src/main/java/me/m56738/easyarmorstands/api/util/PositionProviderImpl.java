package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.NotNull;

class PositionProviderImpl implements PositionProvider {
    private final Vector3dc position;

    PositionProviderImpl(Vector3dc position) {
        this.position = new Vector3d(position);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return position;
    }
}
