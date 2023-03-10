package me.m56738.easyarmorstands.capability.visibility;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Capability(name = "Entity visibility")
public interface VisibilityCapability {
    void showEntity(Player player, Plugin plugin, Entity entity);

    void hideEntity(Player player, Plugin plugin, Entity entity);
}
