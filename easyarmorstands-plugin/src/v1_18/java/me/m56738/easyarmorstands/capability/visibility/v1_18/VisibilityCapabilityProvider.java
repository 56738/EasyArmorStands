package me.m56738.easyarmorstands.capability.visibility.v1_18;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.visibility.VisibilityCapability;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VisibilityCapabilityProvider implements CapabilityProvider<VisibilityCapability> {
    @Override
    public boolean isSupported() {
        try {
            Player.class.getDeclaredMethod("hideEntity", Plugin.class, Entity.class);
            Player.class.getDeclaredMethod("showEntity", Plugin.class, Entity.class);
            Player.class.getDeclaredMethod("canSee", Entity.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public VisibilityCapability create(Plugin plugin) {
        return new VisibilityCapabilityImpl();
    }

    @SuppressWarnings("deprecation")
    private static class VisibilityCapabilityImpl implements VisibilityCapability {
        @Override
        public void hideEntity(Player player, Plugin plugin, Entity entity) {
            player.hideEntity(plugin, entity);
        }

        @Override
        public boolean isNotHidden(Player player, Entity entity) {
            return player.canSee(entity);
        }
    }
}
