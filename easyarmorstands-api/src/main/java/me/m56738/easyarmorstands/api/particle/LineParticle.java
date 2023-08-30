package me.m56738.easyarmorstands.api.particle;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

@ApiStatus.NonExtendable
public interface LineParticle extends ColoredParticle {
    /**
     * Sets the center, rotation, offset and length to connect two points with this line.
     *
     * @param from The first point of the line.
     * @param to   The second point of the line.
     */
    default void setFromTo(@NotNull Vector3dc from, @NotNull Vector3dc to) {
        Vector3dc delta = to.sub(from, new Vector3d());
        if (delta.lengthSquared() < 1e-4) {
            setCenter(from);
            setRotation(new Quaterniond());
            setLength(0);
            setOffset(0);
            return;
        }
        Quaterniondc rotation = new Quaterniond().rotationTo(getAxis().getDirection(), delta);
        double length = delta.length();
        setCenter(from);
        setRotation(rotation);
        setLength(length);
        setOffset(length / 2);
    }

    @NotNull Vector3dc getCenter();

    void setCenter(@NotNull Vector3dc center);

    @NotNull Axis getAxis();

    void setAxis(@NotNull Axis axis);

    double getWidth();

    void setWidth(double width);

    @NotNull Quaterniondc getRotation();

    void setRotation(@NotNull Quaterniondc rotation);

    double getLength();

    void setLength(double length);

    double getOffset();

    void setOffset(double offset);
}
