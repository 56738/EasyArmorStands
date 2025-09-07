package me.m56738.easyarmorstands.modded.platform.world;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.minecraft.server.level.ServerLevel;

public record ModdedWorldImpl(ModdedPlatform platform, ServerLevel nativeWorld) implements ModdedWorld {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ServerLevel getNative() {
        return nativeWorld;
    }
}
