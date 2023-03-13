package me.m56738.easyarmorstands.capability.entityplace.v1_13_2;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.entityplace.EntityPlaceCapability;
import me.m56738.easyarmorstands.capability.entityplace.EntityPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;

public class EntityPlaceCapabilityProvider implements CapabilityProvider<EntityPlaceCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.event.entity.EntityPlaceEvent");
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
    public EntityPlaceCapability create(Plugin plugin) {
        return new EntityPlaceCapabilityImpl(plugin);
    }


    private static class EntityPlaceCapabilityImpl implements EntityPlaceCapability {
        public EntityPlaceCapabilityImpl(Plugin plugin) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        @SuppressWarnings("deprecation")
        public void onArmSwing(org.bukkit.event.entity.EntityPlaceEvent event) {
            Bukkit.getPluginManager().callEvent(new EntityPlaceEvent(event.getEntity(), event.getPlayer()));
        }
    }
}
