package me.m56738.easyarmorstands.modded.api.platform.world;

import net.minecraft.server.level.ServerLevel;

record ModdedWorldImpl(ServerLevel nativeWorld) implements ModdedWorld {
    @Override
    public ServerLevel getNative() {
        return nativeWorld;
    }
}
