package me.m56738.easyarmorstands.capability.entitysize;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

@Capability(name = "Entity size", optional = true)
public interface EntitySizeCapability {
    double getWidth(Entity entity);

    double getHeight(Entity entity);
}
