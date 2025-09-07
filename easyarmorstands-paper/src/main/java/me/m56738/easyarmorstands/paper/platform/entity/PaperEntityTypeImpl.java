package me.m56738.easyarmorstands.paper.platform.entity;

import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntityType;
import org.bukkit.entity.EntityType;

public record PaperEntityTypeImpl(
        PaperPlatform platform, org.bukkit.entity.EntityType nativeType
) implements PaperEntityType {
    @Override
    public PaperPlatform getPlatform() {
        return platform;
    }

    @Override
    public EntityType getNative() {
        return nativeType;
    }
}
