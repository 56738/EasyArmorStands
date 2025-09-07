package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatformHolder;
import net.minecraft.world.level.block.state.BlockState;

public interface ModdedBlockData extends BlockData, ModdedPlatformHolder {
    static BlockState toNative(BlockData data) {
        return ((ModdedBlockData) data).getNative();
    }

    BlockState getNative();
}
