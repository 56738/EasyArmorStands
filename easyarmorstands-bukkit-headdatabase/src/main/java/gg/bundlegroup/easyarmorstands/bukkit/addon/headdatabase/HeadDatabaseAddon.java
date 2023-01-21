package gg.bundlegroup.easyarmorstands.bukkit.addon.headdatabase;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;
import gg.bundlegroup.easyarmorstands.bukkit.addon.Addon;
import org.bukkit.Bukkit;

public class HeadDatabaseAddon implements Addon {
    @Override
    public boolean isSupported() {
        return Bukkit.getPluginManager().getPlugin("HeadDatabase") != null;
    }

    @Override
    public String getName() {
        return "HeadDatabase";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        plugin.getServer().getPluginManager().registerEvents(new HeadDatabaseListener(plugin), plugin);
    }
}
