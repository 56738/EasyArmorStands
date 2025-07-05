package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBoxImpl that = (BoundingBoxImpl) o;
        return Objects.equals(min, that.min) && Objects.equals(max, that.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "BoundingBoxImpl{min=" + min + ", max=" + max + '}';
    }
}
