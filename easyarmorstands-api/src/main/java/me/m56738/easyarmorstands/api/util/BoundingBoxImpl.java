package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

class BoundingBoxImpl implements BoundingBox {
    private final Vector3dc min;
    private final Vector3dc max;

    BoundingBoxImpl(Vector3dc min, Vector3dc max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public @NotNull Vector3dc getMinPosition() {
        return min;
    }

    @Override
    public @NotNull Vector3dc getMaxPosition() {
        return max;
    }
}
