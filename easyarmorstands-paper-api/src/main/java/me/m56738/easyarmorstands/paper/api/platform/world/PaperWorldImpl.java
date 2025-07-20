package me.m56738.easyarmorstands.paper.api.platform.world;

import org.bukkit.World;

record PaperWorldImpl(World nativeWorld) implements PaperWorld {
    @Override
    public World getNative() {
        return nativeWorld;
    }
}
