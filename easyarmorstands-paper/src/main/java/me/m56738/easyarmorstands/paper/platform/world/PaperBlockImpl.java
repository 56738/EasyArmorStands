package me.m56738.easyarmorstands.paper.platform.world;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlock;
import org.bukkit.block.Block;

public record PaperBlockImpl(PaperPlatform platform, Block nativeBlock) implements PaperBlock {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public Block getNative() {
        return nativeBlock;
    }
}
