package me.m56738.easyarmorstands.paper.platform.world;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperWorld;
import org.bukkit.World;

public record PaperWorldImpl(PaperPlatform platform, World nativeWorld) implements PaperWorld {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public World getNative() {
        return nativeWorld;
    }
}
