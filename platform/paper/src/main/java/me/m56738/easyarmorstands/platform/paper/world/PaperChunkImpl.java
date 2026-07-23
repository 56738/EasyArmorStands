package me.m56738.easyarmorstands.platform.paper.world;

import org.bukkit.Chunk;

record PaperChunkImpl(Chunk chunk) implements PaperChunk {
    @Override
    public Chunk getNative() {
        return chunk;
    }
}
