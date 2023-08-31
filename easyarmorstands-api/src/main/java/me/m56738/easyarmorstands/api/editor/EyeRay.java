package me.m56738.easyarmorstands.api.editor;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Matrix4dc;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface EyeRay {
    @Contract(pure = true)
    @NotNull World world();

    @Contract(pure = true)
    @NotNull Vector3dc origin();

    @Contract(pure = true)
    @NotNull Vector3dc target();

    @Contract(pure = true)
    double length();

    @Contract(pure = true)
    double threshold();

    @Contract(pure = true)
    float yaw();

    @Contract(pure = true)
    float pitch();

    @Contract(pure = true)
    @NotNull Matrix4dc matrix();

    @Contract(pure = true)
    @NotNull Matrix4dc inverseMatrix();

    default boolean isInRange(Location location) {
        return world().equals(location.getWorld()) && isInRange(location.getX(), location.getY(), location.getZ());
    }

    default boolean isInRange(Vector3dc position) {
        return isInRange(position.x(), position.y(), position.z());
    }

    default boolean isInRange(double x, double y, double z) {
        double range = length();
        return origin().distanceSquared(x, y, z) <= range * range;
    }

    @Contract(pure = true)
    default @Nullable Vector3dc intersectPoint(Vector3dc position) {
        Vector3dc origin = origin();
        Vector3dc target = target();
        double threshold = threshold();
        Vector3d closestOnEyeRay = Intersectiond.findClosestPointOnLineSegment(
                origin.x(), origin.y(), origin.z(),
                target.x(), target.y(), target.z(),
                position.x(), position.y(), position.z(),
                new Vector3d());
        if (position.distanceSquared(closestOnEyeRay) < threshold * threshold) {
            return position;
        } else {
            return null;
        }
    }

    @Contract(pure = true)
    default @Nullable Vector3dc intersectLine(Vector3dc start, Vector3dc end) {
        Vector3dc eyes = origin();
        Vector3dc target = target();
        Vector3d closestOnLookRay = new Vector3d();
        Vector3d closestOnLine = new Vector3d();
        double distanceSquared = Intersectiond.findClosestPointsLineSegments(
                eyes.x(), eyes.y(), eyes.z(),
                target.x(), target.y(), target.z(),
                start.x(), start.y(), start.z(),
                end.x(), end.y(), end.z(),
                closestOnLookRay,
                closestOnLine
        );

        double threshold = threshold();
        if (distanceSquared < threshold * threshold) {
            return closestOnLine;
        } else {
            return null;
        }
    }

    @Contract(pure = true)
    default @Nullable Vector3dc intersectPlane(Vector3dc point, Vector3dc normal) {
        Vector3dc origin = origin();
        Vector3dc direction = target().sub(origin, new Vector3d());
        double ox = origin.x(), oy = origin.y(), oz = origin.z();
        double dx = direction.x(), dy = direction.y(), dz = direction.z();
        double px = point.x(), py = point.y(), pz = point.z();
        double nx = normal.x(), ny = normal.y(), nz = normal.z();
        double t = Intersectiond.intersectRayPlane(
                ox, oy, oz, dx, dy, dz,
                px, py, pz, nx, ny, nz,
                0.1);
        if (t < 0) {
            // Didn't hit the front, try the back
            t = Intersectiond.intersectRayPlane(
                    ox, oy, oz, dx, dy, dz,
                    px, py, pz, -nx, -ny, -nz,
                    0.1);
        }
        if (t >= 0 && t < 1) {
            return origin.fma(t, direction, new Vector3d());
        } else {
            return null;
        }
    }

    @Contract(pure = true)
    default @Nullable Vector3dc intersectCircle(Vector3dc point, Vector3dc normal, double radius) {
        Vector3dc intersection = intersectPlane(point, normal);
        double threshold = threshold();
        if (intersection != null) {
            // Looking at the plane
            double d = intersection.distanceSquared(point);
            double min = radius - threshold;
            double max = radius + threshold;
            if (d >= min * min && d <= max * max) {
                // Looking at the circle
                return intersection;
            }
        }
        return null;
    }

    @Contract(pure = true)
    default @Nullable Vector3dc intersectBox(Vector3dc center, Vector3dc size) {
        Vector3dc origin = origin();
        Vector3d min = center.fma(-0.5, size, new Vector3d());
        Vector3d max = center.fma(0.5, size, new Vector3d());
        Vector3d direction = target().sub(origin, new Vector3d());
        Vector2d result = new Vector2d();
        if (Intersectiond.intersectRayAab(origin, direction, min, max, result) && result.x <= 1) {
            // result.x contains the distance to the near point
            // 0..1 because direction is not normalized
            if (result.x > 0) {
                // Looking at the box from outside
                return origin.fma(result.x, direction, new Vector3d());
            } else {
                // Inside the box
                return origin;
            }
        }
        return null;
    }
}
