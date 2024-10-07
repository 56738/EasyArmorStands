package me.m56738.easyarmorstands.capability.entitytag;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;

import java.util.Set;

@Capability(name = "Entity tags", optional = true)
public interface EntityTagCapability {
    Set<String> getTags(Entity entity);

    boolean addTag(Entity entity, String tag);

    boolean removeTag(Entity entity, String tag);
}
