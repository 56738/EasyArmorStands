package me.m56738.easyarmorstands.platform.paper.block;

import org.bukkit.block.Block;

record PaperBlockImpl(Block block) implements PaperBlock {
    @Override
    public Block getNative() {
        return block;
    }
}
