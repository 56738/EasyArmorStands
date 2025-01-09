package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.gizmo.api.cursor.Intersection;
import me.m56738.gizmo.api.cursor.RayCursor;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class RayCursorEyeRay implements EyeRay {
    private final World world;
    private final RayCursor cursor;
    private final double threshold;
    private final float yaw;
    private final float pitch;
    private final Matrix4dc matrix;
    private Matrix4dc inverseMatrix;

    public RayCursorEyeRay(World world, RayCursor cursor, double threshold, float yaw, float pitch) {
        this.world = world;
        this.cursor = cursor;
        this.threshold = threshold;
        this.yaw = yaw;
        this.pitch = pitch;
        this.matrix = new Matrix4d()
                .translation(cursor.origin())
                .rotateY(-Math.toRadians(yaw))
                .rotateX(Math.toRadians(pitch));
    }

    @Override
    public @NotNull World world() {
        return world;
    }

    @Override
    public @NotNull Vector3dc origin() {
        return cursor.origin();
    }

    @Override
    public @NotNull Vector3dc target() {
        return cursor.end();
    }

    @Override
    public @NotNull Vector3dc point(double distance) {
        return cursor.origin().fma(distance, cursor.direction(), new Vector3d());
    }

    @Override
    public double length() {
        return cursor.length();
    }

    @Override
    public double threshold() {
        return threshold;
    }

    @Override
    public float yaw() {
        return yaw;
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public @NotNull Matrix4dc matrix() {
        return matrix;
    }

    @Override
    public @NotNull Matrix4dc inverseMatrix() {
        if (inverseMatrix == null) {
            inverseMatrix = matrix.invert(new Matrix4d());
        }
        return inverseMatrix;
    }

    private static @Nullable Vector3dc convert(@Nullable Intersection intersection) {
        if (intersection != null) {
            return intersection.positionOnTarget();
        } else {
            return null;
        }
    }

    @Override
    public @Nullable Vector3dc intersectPoint(@NotNull Vector3dc position) {
        return intersectPoint(position, 1);
    }

    @Override
    public @Nullable Vector3dc intersectPoint(@NotNull Vector3dc position, double scale) {
        return convert(cursor.intersectPoint(position, threshold() * scale));
    }

    @Override
    public @Nullable Vector3dc intersectLine(@NotNull Vector3dc start, @NotNull Vector3dc end) {
        return intersectLine(start, end, 1);
    }

    @Override
    public @Nullable Vector3dc intersectLine(@NotNull Vector3dc start, @NotNull Vector3dc end, double scale) {
        return convert(cursor.intersectLine(start, end, threshold * scale));
    }

    @Override
    public @Nullable Vector3dc intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal) {
        return convert(cursor.intersectPlane(point, normal));
    }

    @Override
    public @Nullable Vector3dc intersectPlane(@NotNull Vector3dc point, @NotNull Vector3dc normal, double range) {
        return convert(cursor.intersectPlane(point, normal, range));
    }

    @Override
    public @Nullable Vector3dc intersectCircle(@NotNull Vector3dc point, @NotNull Vector3dc normal, double radius) {
        return intersectCircle(point, normal, radius, 1);
    }

    @Override
    public @Nullable Vector3dc intersectCircle(@NotNull Vector3dc point, @NotNull Vector3dc normal, double radius, double scale) {
        return convert(cursor.intersectCircle(point, normal, radius, threshold * scale));
    }

    @Override
    public @Nullable Vector3dc intersectBox(@NotNull BoundingBox box) {
        return convert(cursor.intersectBox(box.getMinPosition(), box.getMaxPosition()));
    }
}
