package me.m56738.easyarmorstands.capability.silent;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

@Capability(name = "Silent entities", optional = true)
public interface SilentCapability {
    boolean isSilent(Entity entity);

    void setSilent(Entity entity, boolean silent);
}
