package me.m56738.easyarmorstands.platform.modded.world;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.server.level.ServerLevel;

record ModdedWorldImpl(ModdedPlatform platform, ServerLevel level) implements ModdedWorld {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ServerLevel getNative() {
        return level;
    }
}
