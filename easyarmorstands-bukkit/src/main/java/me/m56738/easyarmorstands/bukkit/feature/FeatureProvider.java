package me.m56738.easyarmorstands.bukkit.feature;

public interface FeatureProvider<T> {
    boolean isSupported();

    default Priority getPriority() {
        return Priority.NORMAL;
    }

    T create();

    enum Priority {
        HIGH,
        NORMAL,
        FALLBACK
    }
}
