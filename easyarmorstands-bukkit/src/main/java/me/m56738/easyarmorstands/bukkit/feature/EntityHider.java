package me.m56738.easyarmorstands.bukkit.feature;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface EntityHider {
    void hideEntity(Plugin plugin, Player player, Entity entity);

    void showEntity(Plugin plugin, Player player, Entity entity);

    interface Provider extends FeatureProvider<EntityHider> {
    }
}
