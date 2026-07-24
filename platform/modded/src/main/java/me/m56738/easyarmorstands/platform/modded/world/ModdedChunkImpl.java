package me.m56738.easyarmorstands.platform.modded.world;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.level.chunk.LevelChunk;

record ModdedChunkImpl(ModdedPlatform platform, LevelChunk chunk) implements ModdedChunk {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public LevelChunk getNative() {
        return chunk;
    }
}
