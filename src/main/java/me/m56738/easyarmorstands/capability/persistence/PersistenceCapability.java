package me.m56738.easyarmorstands.capability.persistence;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

@Capability(name = "Entity persistence")
public interface PersistenceCapability {
    boolean isPersistent(Entity entity);

    void setPersistent(Entity entity, boolean persistent);
}
