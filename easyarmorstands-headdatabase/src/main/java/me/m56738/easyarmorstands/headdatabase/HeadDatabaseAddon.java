package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import org.bukkit.event.HandlerList;

public class HeadDatabaseAddon implements Addon {
    private HeadDatabaseListener listener;

    @Override
    public String name() {
        return "HeadDatabase";
    }

    @Override
    public void enable() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        EasyArmorStands.get().menuSlotTypeRegistry().register(new HeadDatabaseSlotType());
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
