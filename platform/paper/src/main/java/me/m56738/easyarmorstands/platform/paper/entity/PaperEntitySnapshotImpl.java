package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.EntitySnapshot;

record PaperEntitySnapshotImpl(EntitySnapshot snapshot) implements PaperEntitySnapshot {
    @Override
    public EntitySnapshot getNative() {
        return snapshot;
    }
}
