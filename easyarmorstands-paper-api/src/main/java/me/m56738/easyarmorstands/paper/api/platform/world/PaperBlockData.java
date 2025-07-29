package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.BlockData;

public interface PaperBlockData extends BlockData {
    static PaperBlockData fromNative(org.bukkit.block.data.BlockData nativeData) {
        return new PaperBlockDataImpl(nativeData);
    }

    static org.bukkit.block.data.BlockData toNative(BlockData data) {
        return ((PaperBlockData) data).getNative();
    }

    org.bukkit.block.data.BlockData getNative();
}
