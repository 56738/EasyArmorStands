package me.m56738.easyarmorstands.capability.crafter.v1_21_10_spigot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.crafter.CrafterCapability;
import org.bukkit.Material;
import org.bukkit.block.Crafter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CrafterCapabilityProvider implements CapabilityProvider<CrafterCapability> {
    @Override
    public boolean isSupported() {
        try {
            Class.forName("org.bukkit.event.block.CrafterCraftEvent");
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
    public CrafterCapability create(Plugin plugin) {
        return new CrafterCapabilityImpl(plugin);
    }

    private static class CrafterCapabilityImpl implements CrafterCapability {
        private final Plugin plugin;

        public CrafterCapabilityImpl(Plugin plugin) {
            this.plugin = plugin;
        }

        @Override
        public void register() {
            plugin.getServer().getPluginManager().registerEvents(new CrafterListener(), plugin);
        }
    }

    private static class CrafterListener implements Listener {
        @EventHandler
        public void onCrafterCraft(CrafterCraftEvent event) {
            if (event.getBlock().getType() == Material.CRAFTER) {
                Crafter crafter = (Crafter) event.getBlock().getState();
                for (ItemStack item : crafter.getInventory()) {
                    if (EasyArmorStandsPlugin.getInstance().isTool(item)) {
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
