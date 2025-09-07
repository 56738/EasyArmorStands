package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatformHolder;

public interface PaperBlockData extends BlockData, PaperPlatformHolder {
    static org.bukkit.block.data.BlockData toNative(BlockData data) {
        return ((PaperBlockData) data).getNative();
    }

    org.bukkit.block.data.BlockData getNative();
}
