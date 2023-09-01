package me.m56738.easyarmorstands.api.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

@Deprecated
public interface RotateAxis extends EditorAxis {
    @NotNull Vector3dc getPosition();

    @NotNull Quaterniondc getRotation();

    @NotNull Axis getAxis();

    double start();

    void set(double value);

    default boolean isInverted() {
        return false;
    }
}
