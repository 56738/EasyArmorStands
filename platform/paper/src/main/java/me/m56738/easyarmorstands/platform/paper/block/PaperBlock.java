package me.m56738.easyarmorstands.platform.paper.block;

import me.m56738.easyarmorstands.platform.block.Block;

public interface PaperBlock extends Block {
    static PaperBlock fromNative(org.bukkit.block.Block block) {
        return new PaperBlockImpl(block);
    }

    org.bukkit.block.Block getNative();

    static org.bukkit.block.Block toNative(Block block) {
        return ((PaperBlock) block).getNative();
    }

    @Override
    default int blockLight() {
        return getNative().getLightFromBlocks();
    }

    @Override
    default int skyLight() {
        return getNative().getLightFromSky();
    }

    @Override
    default PaperBlockData getBlockData() {
        return PaperBlockData.fromNative(getNative().getBlockData());
    }
}
