package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.common.platform.EasWrapper;

public class BukkitWrapper<T> implements EasWrapper {
    private final BukkitPlatform platform;
    private final T object;

    public BukkitWrapper(BukkitPlatform platform, T object) {
        this.platform = platform;
        this.object = object;
    }

    public T get() {
        return object;
    }

    @Override
    public BukkitPlatform platform() {
        return platform;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return object.equals(((BukkitWrapper<?>) obj).object);
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }
}
