package me.m56738.easyarmorstands.platform.modded.block;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

record ModdedBlockImpl(ModdedPlatform platform, ServerLevel level, BlockPos pos) implements ModdedBlock {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ServerLevel getLevel() {
        return level;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }
}
