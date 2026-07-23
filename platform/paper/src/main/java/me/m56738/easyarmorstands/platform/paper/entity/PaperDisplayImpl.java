package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Display;

record PaperDisplayImpl(Display entity) implements PaperDisplay {
    @Override
    public Display getNative() {
        return entity;
    }
}
