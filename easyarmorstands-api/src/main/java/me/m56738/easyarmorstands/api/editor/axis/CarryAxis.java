package me.m56738.easyarmorstands.api.editor.axis;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface CarryAxis extends EditorAxis {
    Vector3dc getPosition();

    Quaterniondc getRotation();

    void start(@NotNull EyeRay eyeRay);

    void update(@NotNull EyeRay eyeRay);
}
