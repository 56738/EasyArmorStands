package me.m56738.easyarmorstands.modded.api.platform.world;

import net.minecraft.world.level.Level;

record ModdedWorldImpl(Level nativeWorld) implements ModdedWorld {
    @Override
    public Level getNative() {
        return nativeWorld;
    }
}
