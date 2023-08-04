package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.particle.ParticleColor;
import org.joml.Vector3dc;

public enum Axis {
    X("X", ParticleColor.RED, Util.X),
    Y("Y", ParticleColor.GREEN, Util.Y),
    Z("Z", ParticleColor.BLUE, Util.Z);

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
}
