package me.m56738.easyarmorstands.api.context;

import org.bukkit.entity.Player;

public interface ChangeContextFactory {
    ManagedChangeContext create(Player player);
}
