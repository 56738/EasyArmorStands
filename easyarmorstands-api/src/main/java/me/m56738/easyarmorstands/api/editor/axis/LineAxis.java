package me.m56738.easyarmorstands.api.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface LineAxis extends EditorAxis {
    @NotNull Vector3dc getPosition();

    @NotNull Quaterniondc getRotation();

    @NotNull Axis getAxis();

    double start();

    void set(double value);

    default double getMinValue() {
        return Double.NEGATIVE_INFINITY;
    }

    default double getMaxValue() {
        return Double.POSITIVE_INFINITY;
    }

    default boolean isInverted() {
        return false;
    }
}
