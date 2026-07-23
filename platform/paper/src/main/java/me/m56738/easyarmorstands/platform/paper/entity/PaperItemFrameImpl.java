package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.ItemFrame;

record PaperItemFrameImpl(ItemFrame entity) implements PaperItemFrame {
    @Override
    public ItemFrame getNative() {
        return entity;
    }
}
