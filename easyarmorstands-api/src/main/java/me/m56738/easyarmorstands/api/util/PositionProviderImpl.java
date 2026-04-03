package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

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
