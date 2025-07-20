package me.m56738.easyarmorstands.modded.platform;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;

public class ModdedPlatformImpl implements ModdedPlatform {
    private final String modVersion;

    public ModdedPlatformImpl(String modVersion) {
        this.modVersion = modVersion;
    }

    @Override
    public String getEasyArmorStandsVersion() {
        return modVersion;
    }
}
