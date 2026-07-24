package me.m56738.easyarmorstands.platform.modded.block;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;

public interface ModdedBlock extends Block, ModdedPlatformHolder {
    static ModdedBlock fromNative(ModdedPlatform platform, ServerLevel level, BlockPos pos) {
        return new ModdedBlockImpl(platform, level, pos);
    }

    ServerLevel getLevel();

    BlockPos getPos();

    @Override
    default int blockLight() {
        return getLevel().getBrightness(LightLayer.BLOCK, getPos());
    }

    @Override
    default int skyLight() {
        return getLevel().getBrightness(LightLayer.SKY, getPos());
    }

    @Override
    default BlockData getBlockData() {
        return ModdedBlockData.fromNative(getPlatform(), getLevel().getBlockState(getPos()));
    }
}
