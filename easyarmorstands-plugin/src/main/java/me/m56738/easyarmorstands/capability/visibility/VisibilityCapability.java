package me.m56738.easyarmorstands.capability.visibility;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Capability(name = "Entity visibility", optional = true)
public interface VisibilityCapability {
    void hideEntity(Player player, Plugin plugin, Entity entity);

    boolean isNotHidden(Player player, Entity entity);
}
