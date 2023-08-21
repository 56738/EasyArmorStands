package me.m56738.easyarmorstands.capability.lookup;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

import java.util.UUID;

@Capability(name = "Entity lookup")
public interface LookupCapability {
    Entity getEntity(UUID uuid);
}
