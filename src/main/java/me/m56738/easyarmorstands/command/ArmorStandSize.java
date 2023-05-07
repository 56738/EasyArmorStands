package me.m56738.easyarmorstands.command;

import org.bukkit.entity.ArmorStand;

import java.util.Locale;

public enum ArmorStandSize {
    SMALL,
    NORMAL;

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
}
