package me.m56738.easyarmorstands.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import net.kyori.adventure.text.ComponentLike;

public interface EntityType extends ComponentLike, PlatformHolder {
    String name();
}
