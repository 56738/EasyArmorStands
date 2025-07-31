package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public interface ModdedBlock extends Block {
    ServerLevel getLevel();

    BlockPos getPos();

    default BlockState getState() {
        return getLevel().getBlockState(getPos());
    }

    @Override
    default BlockData getBlockData() {
        return new ModdedBlockDataImpl(getState());
    }

    @Override
    default Brightness getBrightness() {
        return Brightness.of(
                getLevel().getBrightness(LightLayer.BLOCK, getPos()),
                getLevel().getBrightness(LightLayer.SKY, getPos()));
    }
}
