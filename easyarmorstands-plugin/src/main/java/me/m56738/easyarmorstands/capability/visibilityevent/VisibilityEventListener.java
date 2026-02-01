package me.m56738.easyarmorstands.capability.visibilityevent;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@FunctionalInterface
public interface VisibilityEventListener {
    void onVisibilityChanged(Player player, Entity entity, boolean visible);
}
