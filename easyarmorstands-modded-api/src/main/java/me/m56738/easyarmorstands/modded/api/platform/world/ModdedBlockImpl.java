package me.m56738.easyarmorstands.modded.api.platform.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public record ModdedBlockImpl(ServerLevel level, BlockPos pos) implements ModdedBlock {
    @Override
    public ServerLevel getLevel() {
        return level;
    }

    @Override
    public BlockPos getPos() {
        return pos;
    }
}
