package me.m56738.easyarmorstands.paper.platform.world;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import org.bukkit.block.data.BlockData;

public record PaperBlockDataImpl(PaperPlatform platform, BlockData nativeData) implements PaperBlockData {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public BlockData getNative() {
        return nativeData;
    }
}
