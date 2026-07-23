package me.m56738.easyarmorstands.platform.paper.world;

import me.m56738.easyarmorstands.platform.world.Chunk;

public interface PaperChunk extends Chunk {
    static PaperChunk fromNative(org.bukkit.Chunk chunk) {
        return new PaperChunkImpl(chunk);
    }

    org.bukkit.Chunk getNative();

    static org.bukkit.Chunk toNative(Chunk chunk) {
        return ((PaperChunk) chunk).getNative();
    }
}
