package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.entity.BlockDisplay;
import me.m56738.easyarmorstands.platform.paper.block.PaperBlockData;

public interface PaperBlockDisplay extends BlockDisplay, PaperDisplay {
    static PaperBlockDisplay fromNative(org.bukkit.entity.BlockDisplay entity) {
        return new PaperBlockDisplayImpl(entity);
    }

    org.bukkit.entity.BlockDisplay getNative();

    static org.bukkit.entity.BlockDisplay toNative(BlockDisplay entity) {
        return ((PaperBlockDisplay) entity).getNative();
    }

    @Override
    default BlockData getBlock() {
        return PaperBlockData.fromNative(getNative().getBlock());
    }

    @Override
    default void setBlock(BlockData data) {
        getNative().setBlock(PaperBlockData.toNative(data));
    }
}
