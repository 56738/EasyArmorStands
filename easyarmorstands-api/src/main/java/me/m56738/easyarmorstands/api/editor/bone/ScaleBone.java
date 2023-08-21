package me.m56738.easyarmorstands.api.editor.bone;

import me.m56738.easyarmorstands.api.Axis;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface ScaleBone extends Bone {
    Vector3dc getOrigin();

    Quaterniondc getRotation();

    double getScale(Axis axis);

    void setScale(Axis axis, double scale);
}
