package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Interaction;

record PaperInteractionImpl(Interaction entity) implements PaperInteraction {
    @Override
    public Interaction getNative() {
        return entity;
    }
}
