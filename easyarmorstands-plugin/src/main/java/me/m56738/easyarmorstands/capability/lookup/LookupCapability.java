package me.m56738.easyarmorstands.capability.lookup;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Capability(name = "Entity lookup")
public interface LookupCapability {
    Entity getEntity(UUID uuid, @Nullable Chunk expectedChunk);
}
