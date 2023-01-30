package me.m56738.easyarmorstands.bukkit.feature.v1_18;

import me.m56738.easyarmorstands.bukkit.feature.EntityHider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("UnstableApiUsage")
public class EntityHiderImpl implements EntityHider {
    @Override
    public void hideEntity(Plugin plugin, Player player, Entity entity) {
        player.hideEntity(plugin, entity);
    }

    @Override
    public void showEntity(Plugin plugin, Player player, Entity entity) {
        player.showEntity(plugin, entity);
    }

    public static class Provider implements EntityHider.Provider {
        @Override
        public boolean isSupported() {
            try {
                Player.class.getDeclaredMethod("hideEntity", Plugin.class, Entity.class);
                Player.class.getDeclaredMethod("showEntity", Plugin.class, Entity.class);
                return true;
            } catch (Throwable e) {
                return false;
            }
        }

        @Override
        public EntityHider create() {
            return new EntityHiderImpl();
        }
    }
}
