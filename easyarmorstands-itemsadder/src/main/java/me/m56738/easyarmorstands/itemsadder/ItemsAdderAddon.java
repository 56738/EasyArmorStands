package me.m56738.easyarmorstands.itemsadder;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;

public class ItemsAdderAddon implements Addon {
    private ItemsAdderListener listener;

    @Override
    public String name() {
        return "ItemsAdder";
    }

    @Override
    public void enable() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        listener = new ItemsAdderListener();
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
