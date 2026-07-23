package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Mannequin;

record PaperMannequinImpl(Mannequin entity) implements PaperMannequin {
    @Override
    public Mannequin getNative() {
        return entity;
    }
}
