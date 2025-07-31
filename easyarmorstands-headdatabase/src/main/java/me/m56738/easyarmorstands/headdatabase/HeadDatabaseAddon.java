package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class HeadDatabaseAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private HeadDatabaseListener listener;

    public HeadDatabaseAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "HeadDatabase";
    }

    @Override
    public void enable() {
        // TODO slot
        listener = new HeadDatabaseListener(plugin);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(listener);
    }

    @Override
    public void reload() {
    }
}
