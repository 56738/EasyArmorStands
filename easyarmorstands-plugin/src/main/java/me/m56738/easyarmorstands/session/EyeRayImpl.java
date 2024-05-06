package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

public class EyeRayImpl implements EyeRay {
    private final World world;
    private final Vector3dc origin;
    private final Vector3dc target;
    private final double length;
    private final double threshold;
    private final float yaw;
    private final float pitch;
    private final Matrix4dc matrix;
    private Matrix4dc inverseMatrix;

    public EyeRayImpl(World world, Vector3dc origin, Vector3dc target, double length, double threshold, float yaw, float pitch, Matrix4dc matrix) {
        this.world = world;
        this.origin = origin;
        this.target = target;
        this.length = length;
        this.threshold = threshold;
        this.yaw = yaw;
        this.pitch = pitch;
        this.matrix = matrix;
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
}
