package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.block.BlockData;

public interface BlockDisplay extends Display {
    BlockData getBlock();

    void setBlock(BlockData data);
}
