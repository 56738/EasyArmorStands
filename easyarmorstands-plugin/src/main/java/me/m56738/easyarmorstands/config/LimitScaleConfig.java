package me.m56738.easyarmorstands.config;

import me.m56738.easyarmorstands.lib.configurate.objectmapping.ConfigSerializable;
import org.joml.Math;
import org.joml.Vector3f;

@ConfigSerializable
public class LimitScaleConfig {
    public double minScale;
    public double maxScale;

    public double clampScale(double scale) {
        return Math.clamp(minScale, maxScale, scale);
    }

    public float clampScale(float scale) {
        return Math.clamp((float) minScale, (float) maxScale, scale);
    }

    public void clampScale(Vector3f scale) {
        scale.set(clampScale(scale.x()), clampScale(scale.y()), clampScale(scale.z()));
    }
}
