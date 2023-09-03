package me.m56738.easyarmorstands.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Arrays;

public interface BoundingBox {
    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull BoundingBox box) {
        return new BoundingBoxImpl(
                new Vector3d(box.getMinPosition()),
                new Vector3d(box.getMaxPosition()));
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull BoundingBox a, @NotNull BoundingBox b) {
        Vector3d min = a.getMinPosition().min(b.getMinPosition(), new Vector3d());
        Vector3d max = a.getMaxPosition().max(b.getMaxPosition(), new Vector3d());
        return new BoundingBoxImpl(min, max);
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull BoundingBox box, @NotNull Vector3dc position) {
        Vector3d min = box.getMinPosition().min(position, new Vector3d());
        Vector3d max = box.getMaxPosition().max(position, new Vector3d());
        return new BoundingBoxImpl(min, max);
    }

    @Contract(pure = true)
    static @NotNull BoundingBox of(@NotNull BoundingBox box, @NotNull Vector3dc @NotNull ... positions) {
        Vector3d min = new Vector3d(box.getMinPosition());
        Vector3d max = new Vector3d(box.getMaxPosition());
        for (Vector3dc position : positions) {
            min.min(position);
            max.max(position);
        }
        return new BoundingBoxImpl(min, max);
    }

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

    @Contract(pure = true)
    default @NotNull Vector3d getCenter(Vector3d dest) {
        Vector3dc min = getMinPosition();
        Vector3dc max = getMaxPosition();
        return max.sub(min, dest).div(2).add(min);
    }

    @Contract(pure = true)
    default boolean contains(@NotNull Vector3dc point) {
        Vector3dc min = getMinPosition();
        Vector3dc max = getMaxPosition();
        return point.x() >= min.x() && point.x() <= max.x()
                && point.y() >= min.y() && point.y() <= max.y()
                && point.z() >= min.z() && point.z() <= max.z();
    }

    @Contract(pure = true)
    default boolean overlaps(@NotNull BoundingBox box) {
        Vector3dc min1 = getMinPosition();
        Vector3dc max1 = getMaxPosition();
        Vector3dc min2 = box.getMinPosition();
        Vector3dc max2 = box.getMaxPosition();
        return min1.x() < max2.x() && max1.x() > min2.x()
                && min1.y() < max2.y() && max1.y() > min2.y()
                && min1.z() < max2.z() && max1.z() > min2.z();
    }
}
