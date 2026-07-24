package me.m56738.easyarmorstands.platform.modded.world;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.world.Chunk;
import net.minecraft.world.level.chunk.LevelChunk;

public interface ModdedChunk extends Chunk, ModdedPlatformHolder {
    LevelChunk getNative();

    static ModdedChunk fromNative(ModdedPlatform platform, LevelChunk chunk) {
        return new ModdedChunkImpl(platform, chunk);
    }

    static LevelChunk toNative(Chunk chunk) {
        return ((ModdedChunk) chunk).getNative();
    }
}
