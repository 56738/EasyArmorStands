package me.m56738.easyarmorstands.modded.platform.world;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public record ModdedBlockImpl(ModdedPlatform platform, ServerLevel level, BlockPos pos) implements ModdedBlock {
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
