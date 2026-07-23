package me.m56738.easyarmorstands.platform.paper.block;

import org.bukkit.block.data.BlockData;

record PaperBlockDataImpl(BlockData data) implements PaperBlockData {
    @Override
    public BlockData getNative() {
        return data;
    }
}
