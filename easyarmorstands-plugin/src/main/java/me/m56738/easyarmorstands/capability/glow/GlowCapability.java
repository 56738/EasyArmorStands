package me.m56738.easyarmorstands.capability.glow;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

@Capability(name = "Glowing entities", optional = true)
public interface GlowCapability {
    boolean isGlowing(Entity entity);

    void setGlowing(Entity entity, boolean glowing);
}
