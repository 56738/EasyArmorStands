package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.BlockDisplay;

record PaperBlockDisplayImpl(BlockDisplay entity) implements PaperBlockDisplay {
    @Override
    public BlockDisplay getNative() {
        return entity;
    }
}
