package me.m56738.easyarmorstands.modded.platform.world;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedBlockData;
import net.minecraft.world.level.block.state.BlockState;

public record ModdedBlockDataImpl(ModdedPlatform platform, BlockState nativeData) implements ModdedBlockData {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public BlockState getNative() {
        return nativeData;
    }
}
