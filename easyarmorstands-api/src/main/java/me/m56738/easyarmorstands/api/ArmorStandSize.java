package me.m56738.easyarmorstands.api;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.ArmorStand;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum ArmorStandSize {
    SMALL(0.25, 0.9875, Component.translatable("easyarmorstands.property.armor-stand.size.small")),
    NORMAL(0.5, 1.975, Component.translatable("easyarmorstands.property.armor-stand.size.normal"));

    private final double width;
    private final double height;
    private final Component displayName;

    ArmorStandSize(double width, double height, Component displayName) {
        this.width = width;
        this.height = height;
        this.displayName = displayName;
    }

    public static @NotNull ArmorStandSize get(@NotNull ArmorStand entity) {
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

    public Component displayName() {
        return displayName;
    }
}
