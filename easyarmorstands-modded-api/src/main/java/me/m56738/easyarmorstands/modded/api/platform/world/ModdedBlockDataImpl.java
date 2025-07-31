package me.m56738.easyarmorstands.modded.api.platform.world;

import net.minecraft.world.level.block.state.BlockState;

public record ModdedBlockDataImpl(BlockState nativeData) implements ModdedBlockData {
    @Override
    public BlockState getNative() {
        return nativeData;
    }
}
