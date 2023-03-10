package me.m56738.easyarmorstands.capability.armswing.v1_18_paper;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.armswing.ArmSwingCapability;
import me.m56738.easyarmorstands.capability.armswing.ArmSwingEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

public class ArmSwingCapabilityProvider implements CapabilityProvider<ArmSwingCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("io.papermc.paper.event.player.PlayerArmSwingEvent");
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
    public ArmSwingCapability create(Plugin plugin) {
        return new ArmSwingCapabilityImpl(plugin);
    }

    private static class ArmSwingCapabilityImpl implements ArmSwingCapability {
        public ArmSwingCapabilityImpl(Plugin plugin) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        @EventHandler
        public void onArmSwing(PlayerArmSwingEvent event) {
            ArmSwingEvent wrapper = new ArmSwingEvent(event.getPlayer(), event.getHand(), event.isCancelled());
            Bukkit.getPluginManager().callEvent(wrapper);
            event.setCancelled(wrapper.isCancelled());
        }
    }
}
