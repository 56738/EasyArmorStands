package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.particle.ParticleColor;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public enum Axis {
    X("X", ParticleColor.RED, Util.X) {
        @Override
        public float getValue(Vector3fc source) {
            return source.x();
        }

        @Override
        public double getValue(Vector3dc source) {
            return source.x();
        }

        @Override
        public void setValue(Vector3f target, float value) {
            target.x = value;
        }

        @Override
        public void setValue(Vector3d target, double value) {
            target.x = value;
        }
    },
    Y("Y", ParticleColor.GREEN, Util.Y) {
        @Override
        public float getValue(Vector3fc source) {
            return source.y();
        }

        @Override
        public double getValue(Vector3dc source) {
            return source.y();
        }

        @Override
        public void setValue(Vector3f target, float value) {
            target.y = value;
        }

        @Override
        public void setValue(Vector3d target, double value) {
            target.y = value;
        }
    },
    Z("Z", ParticleColor.BLUE, Util.Z) {
        @Override
        public float getValue(Vector3fc source) {
            return source.z();
        }

        @Override
        public double getValue(Vector3dc source) {
            return source.z();
        }

        @Override
        public void setValue(Vector3f target, float value) {
            target.z = value;
        }

        @Override
        public void setValue(Vector3d target, double value) {
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

    public String getName() {
        return name;
    }

    public ParticleColor getColor() {
        return color;
    }

    public Vector3dc getDirection() {
        return direction;
    }

    public abstract float getValue(Vector3fc source);

    public abstract double getValue(Vector3dc source);

    public abstract void setValue(Vector3f target, float value);

    public abstract void setValue(Vector3d target, double value);
}
