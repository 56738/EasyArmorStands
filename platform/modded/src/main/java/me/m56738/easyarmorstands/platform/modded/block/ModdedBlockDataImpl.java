package me.m56738.easyarmorstands.platform.modded.block;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.level.block.state.BlockState;

record ModdedBlockDataImpl(ModdedPlatform platform, BlockState state) implements ModdedBlockData {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public BlockState getNative() {
        return state;
    }
}
