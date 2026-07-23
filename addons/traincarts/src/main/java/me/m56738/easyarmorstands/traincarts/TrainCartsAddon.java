package me.m56738.easyarmorstands.traincarts;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.paper.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class TrainCartsAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsCommon eas;

    private TrainCartsAddonListener listener;

    public TrainCartsAddon(Plugin plugin, EasyArmorStandsCommon eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "TrainCarts";
    }

    @Override
    public void enable() {
        listener = new TrainCartsAddonListener(plugin, eas);
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
