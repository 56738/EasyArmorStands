package gg.bundlegroup.easyarmorstands.platform.bukkit.feature;

public interface FeatureProvider<T> {
    boolean isSupported();

    T create();
}
