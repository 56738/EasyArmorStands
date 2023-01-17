package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasWrapper;

public class BukkitWrapper implements EasWrapper {
    private final BukkitPlatform platform;
    private final Object object;

    public BukkitWrapper(BukkitPlatform platform, Object object) {
        this.platform = platform;
        this.object = object;
    }

    @Override
    public BukkitPlatform platform() {
        return platform;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return object.equals(((BukkitWrapper) obj).object);
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }
}
