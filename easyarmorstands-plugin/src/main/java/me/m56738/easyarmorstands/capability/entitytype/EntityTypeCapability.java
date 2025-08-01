package me.m56738.easyarmorstands.capability.entitytype;

import me.m56738.easyarmorstands.capability.Capability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;

@Capability(name = "Entity type")
public interface EntityTypeCapability {
    Component getName(EntityType type);
}
