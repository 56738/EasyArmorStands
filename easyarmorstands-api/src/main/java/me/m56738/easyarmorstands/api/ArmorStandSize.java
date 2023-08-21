package me.m56738.easyarmorstands.api;

import org.bukkit.entity.ArmorStand;

import java.util.Locale;

public enum ArmorStandSize {
    SMALL(0.25, 0.9875),
    NORMAL(0.5, 1.975);

    private final double width;
    private final double height;

    ArmorStandSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public static ArmorStandSize get(ArmorStand entity) {
        return entity.isSmall() ? SMALL : NORMAL;
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean isSmall() {
        return this == SMALL;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
