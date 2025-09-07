package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import org.bukkit.entity.Entity;

public record PaperEntityImpl(PaperPlatform platform, Entity nativeEntity) implements PaperEntity {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public Entity getNative() {
        return nativeEntity;
    }
}
