package me.m56738.easyarmorstands.modded.api;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;

public interface EasyArmorStandsModded extends EasyArmorStands {
    @Override
    ModdedPlatform platform();
}
