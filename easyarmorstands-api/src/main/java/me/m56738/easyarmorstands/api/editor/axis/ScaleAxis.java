package me.m56738.easyarmorstands.api.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface ScaleAxis extends EditorAxis {
    Vector3dc getPosition();

    Quaterniondc getRotation();

    Axis getAxis();

    double start();

    void set(double value);

    default double getMinValue() {
        return Double.NEGATIVE_INFINITY;
    }

    default double getMaxValue() {
        return Double.POSITIVE_INFINITY;
    }
}
