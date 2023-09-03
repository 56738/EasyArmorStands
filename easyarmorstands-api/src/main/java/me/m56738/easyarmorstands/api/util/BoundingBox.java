package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Arrays;

public interface BoundingBox {
    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull Vector3dc position) {
        Vector3dc v = new Vector3d(position);
        return new BoundingBoxImpl(v, v);
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull Vector3dc position, double width, double height) {
        Vector3dc min = position.sub(width / 2, 0, width / 2, new Vector3d());
        Vector3dc max = position.add(width / 2, height, width / 2, new Vector3d());
        return new BoundingBoxImpl(min, max);
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull Vector3dc a, @NotNull Vector3dc b) {
        Vector3d min = a.min(b, new Vector3d());
        Vector3d max = a.max(b, new Vector3d());
        return new BoundingBoxImpl(min, max);
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull Vector3dc @NotNull ... positions) {
        return BoundingBox.of(Arrays.asList(positions));
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull Iterable<@NotNull Vector3dc> positions) {
        Vector3d min = new Vector3d(Double.POSITIVE_INFINITY);
        Vector3d max = new Vector3d(Double.NEGATIVE_INFINITY);
        boolean valid = false;
        for (Vector3dc position : positions) {
            min.min(position);
            max.max(position);
            valid = true;
        }
        if (!valid) {
            throw new IllegalArgumentException("Cannot create a bounding box without any position");
        }
        return new BoundingBoxImpl(min, max);
    }

    @Contract(pure = true)
    @NotNull Vector3dc getMinPosition();

    @Contract(pure = true)
    @NotNull Vector3dc getMaxPosition();
}
