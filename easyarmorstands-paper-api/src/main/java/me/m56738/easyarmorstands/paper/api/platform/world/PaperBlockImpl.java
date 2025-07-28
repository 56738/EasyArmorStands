package me.m56738.easyarmorstands.paper.api.platform.world;

import org.bukkit.block.Block;

record PaperBlockImpl(Block nativeBlock) implements PaperBlock {
    @Override
    public Block getNative() {
        return nativeBlock;
    }
}
