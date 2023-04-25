package me.m56738.easyarmorstands.util;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.joml.Vector3dc;

public enum Axis {
    X("X", NamedTextColor.RED, Util.X),
    Y("Y", NamedTextColor.GREEN, Util.Y),
    Z("Z", NamedTextColor.BLUE, Util.Z);

    private final String name;
    private final TextColor color;
    private final Vector3dc direction;

    Axis(String name, TextColor color, Vector3dc direction) {
        this.name = name;
        this.color = color;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public TextColor getColor() {
        return color;
    }

    public Vector3dc getDirection() {
        return direction;
    }
}
