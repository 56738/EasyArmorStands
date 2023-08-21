package me.m56738.easyarmorstands.capability.persistence;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

@Capability(name = "Entity persistence", optional = true)
public interface PersistenceCapability {
    void setPersistent(Entity entity, boolean persistent);
}
