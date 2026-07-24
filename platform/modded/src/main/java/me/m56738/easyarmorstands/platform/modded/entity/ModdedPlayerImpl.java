package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.server.level.ServerPlayer;

record ModdedPlayerImpl(ModdedPlatform platform, ServerPlayer entity) implements ModdedPlayer {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ServerPlayer getNative() {
        return entity;
    }
}
