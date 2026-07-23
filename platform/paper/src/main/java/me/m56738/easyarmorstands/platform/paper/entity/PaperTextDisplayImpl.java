package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.TextDisplay;

record PaperTextDisplayImpl(TextDisplay entity) implements PaperTextDisplay {
    @Override
    public TextDisplay getNative() {
        return entity;
    }
}
