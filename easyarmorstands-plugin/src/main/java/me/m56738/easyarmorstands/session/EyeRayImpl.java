package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.lib.gizmo.api.cursor.Cursor;
import me.m56738.easyarmorstands.lib.gizmo.api.cursor.Intersection;
import me.m56738.easyarmorstands.lib.joml.Matrix4d;
import me.m56738.easyarmorstands.lib.joml.Matrix4dc;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EyeRayImpl implements EyeRay {
    private final World world;
    private final Vector3dc origin;
    private final Vector3dc direction;
    private final Vector3dc target;
    private final double length;
    private final Cursor cursor;
    private final double threshold;
    private final float yaw;
    private final float pitch;
    private final Matrix4dc matrix;
    private Matrix4dc inverseMatrix;

    public EyeRayImpl(World world, Location location, double length, double threshold) {
        this.world = world;
        this.origin = Util.toVector3d(location);
        this.direction = Util.toVector3d(location.getDirection());
        this.target = origin.fma(length, direction, new Vector3d());
        this.length = length;
        this.cursor = Cursor.of(origin, target);
        this.threshold = threshold;
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.matrix = Util.toMatrix4d(location);
    }

    private static @Nullable Vector3dc convert(@Nullable Intersection intersection) {
        if (intersection != null) {
            return intersection.positionOnTarget();
        } else {
            return null;
        }
    }

    @Override
    public @NotNull World world() {
        return world;
    }

    @Override
    public @NotNull Vector3dc origin() {
        return origin;
    }

    @Override
    public @NotNull Vector3dc target() {
        return target;
    }

    @Override
    public @NotNull Vector3dc point(double distance) {
        return origin.fma(distance, direction, new Vector3d());
    }

    @Override
    public double length() {
        return length;
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
