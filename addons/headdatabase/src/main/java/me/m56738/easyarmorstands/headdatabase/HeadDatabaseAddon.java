package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class HeadDatabaseAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;

    private HeadDatabaseListener listener;

    public HeadDatabaseAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "HeadDatabase";
    }

    @Override
    public void enable() {
        listener = new HeadDatabaseListener(eas);
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
