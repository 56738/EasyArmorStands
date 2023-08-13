package me.m56738.easyarmorstands.particle;

import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.Util;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface LineParticle extends ColoredParticle {
    /**
     * Sets the center, rotation, offset and length to connect two points with this line.
     *
     * @param from The first point of the line.
     * @param to   The second point of the line.
     */
    default void setFromTo(Vector3dc from, Vector3dc to) {
        Vector3dc delta = to.sub(from, new Vector3d());
        if (delta.lengthSquared() < 1e-4) {
            setCenter(from);
            setRotation(Util.IDENTITY);
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

    Vector3dc getCenter();

    void setCenter(Vector3dc center);

    Axis getAxis();

    void setAxis(Axis axis);

    double getWidth();

    void setWidth(double width);

    Quaterniondc getRotation();

    void setRotation(Quaterniondc rotation);

    double getLength();

    void setLength(double length);

    double getOffset();

    void setOffset(double offset);
}
