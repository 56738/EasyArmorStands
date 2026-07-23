package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Mob;

record PaperMobImpl(Mob entity) implements PaperMob {
    @Override
    public Mob getNative() {
        return entity;
    }
}
