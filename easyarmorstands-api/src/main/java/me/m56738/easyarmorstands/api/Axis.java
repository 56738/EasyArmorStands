package me.m56738.easyarmorstands.api;

import me.m56738.easyarmorstands.api.particle.ParticleColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public enum Axis {
    X("X", ParticleColor.RED, new Vector3d(1, 0, 0)) {
        @Override
        public float getValue(@NotNull Vector3fc source) {
            return source.x();
        }

        @Override
        public double getValue(@NotNull Vector3dc source) {
            return source.x();
        }

        @Override
        public void setValue(@NotNull Vector3f target, float value) {
            target.x = value;
        }

        @Override
        public void setValue(@NotNull Vector3d target, double value) {
            target.x = value;
        }
    },
    Y("Y", ParticleColor.GREEN, new Vector3d(0, 1, 0)) {
        @Override
        public float getValue(@NotNull Vector3fc source) {
            return source.y();
        }

        @Override
        public double getValue(@NotNull Vector3dc source) {
            return source.y();
        }

        @Override
        public void setValue(@NotNull Vector3f target, float value) {
            target.y = value;
        }

        @Override
        public void setValue(@NotNull Vector3d target, double value) {
            target.y = value;
        }
    },
    Z("Z", ParticleColor.BLUE, new Vector3d(0, 0, 1)) {
        @Override
        public float getValue(@NotNull Vector3fc source) {
            return source.z();
        }

        @Override
        public double getValue(@NotNull Vector3dc source) {
            return source.z();
        }

        @Override
        public void setValue(@NotNull Vector3f target, float value) {
            target.z = value;
        }

        @Override
        public void setValue(@NotNull Vector3d target, double value) {
            target.z = value;
        }
    };

    private final String name;
    private final ParticleColor color;
    private final Vector3dc direction;

    Axis(String name, ParticleColor color, Vector3dc direction) {
        this.name = name;
        this.color = color;
        this.direction = direction;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull ParticleColor getColor() {
        return color;
    }

    public @NotNull Vector3dc getDirection() {
        return direction;
    }

    public abstract float getValue(@NotNull Vector3fc source);

    public abstract double getValue(@NotNull Vector3dc source);

    public abstract void setValue(@NotNull Vector3f target, float value);

    public abstract void setValue(@NotNull Vector3d target, double value);
}
