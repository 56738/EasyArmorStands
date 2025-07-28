package me.m56738.easyarmorstands.common.platform;

import org.jspecify.annotations.Nullable;

public class PlatformHolder {
    public PlatformHolder() {
    }

    public PlatformHolder(@Nullable CommonPlatform platform) {
        this.platform = platform;
    }

    private @Nullable CommonPlatform platform;

    public @Nullable CommonPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(@Nullable CommonPlatform platform) {
        this.platform = platform;
    }
}
