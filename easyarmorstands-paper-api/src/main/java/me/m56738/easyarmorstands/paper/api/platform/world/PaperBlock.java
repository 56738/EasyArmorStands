package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.Block;

public interface PaperBlock extends Block {
    static PaperBlock fromNative(org.bukkit.block.Block nativeBlock) {
        return new PaperBlockImpl(nativeBlock);
    }

    static org.bukkit.block.Block toNative(Block block) {
        return ((PaperBlock) block).getNative();
    }

    org.bukkit.block.Block getNative();
}
