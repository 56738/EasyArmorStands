package me.m56738.easyarmorstands.modded.api.platform;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;

public interface ModdedPlatformHolder extends PlatformHolder {
    @Override
    ModdedPlatform getPlatform();
}
