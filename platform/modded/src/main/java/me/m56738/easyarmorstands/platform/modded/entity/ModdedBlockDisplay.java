package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.entity.BlockDisplay;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlockData;
import net.minecraft.world.entity.Display;

public interface ModdedBlockDisplay extends BlockDisplay, ModdedDisplay {
    @Override
    Display.BlockDisplay getNative();

    static ModdedBlockDisplay fromNative(ModdedPlatform platform, Display.BlockDisplay entity) {
        return new ModdedBlockDisplayImpl(platform, entity);
    }

    static Display.BlockDisplay toNative(BlockDisplay display) {
        return ((ModdedBlockDisplay) display).getNative();
    }

    @Override
    default BlockData getBlock() {
        return ModdedBlockData.fromNative(getPlatform(), getNative().getBlockState());
    }

    @Override
    default void setBlock(BlockData data) {
        getNative().setBlockState(ModdedBlockData.toNative(data));
    }
}
