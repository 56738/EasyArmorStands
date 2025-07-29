package me.m56738.easyarmorstands.paper.api.platform.world;

import org.bukkit.block.data.BlockData;

record PaperBlockDataImpl(BlockData nativeData) implements PaperBlockData {
    @Override
    public BlockData getNative() {
        return nativeData;
    }
}
