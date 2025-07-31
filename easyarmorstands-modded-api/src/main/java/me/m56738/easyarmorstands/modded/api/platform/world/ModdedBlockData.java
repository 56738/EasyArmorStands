package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import net.minecraft.world.level.block.state.BlockState;

public interface ModdedBlockData extends BlockData {
    static ModdedBlockData fromNative(BlockState nativeData) {
        return new ModdedBlockDataImpl(nativeData);
    }

    static BlockState toNative(BlockData data) {
        return ((ModdedBlockData) data).getNative();
    }

    BlockState getNative();
}
