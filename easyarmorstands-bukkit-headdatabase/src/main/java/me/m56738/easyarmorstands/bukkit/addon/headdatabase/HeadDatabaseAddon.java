package me.m56738.easyarmorstands.bukkit.addon.headdatabase;

import me.m56738.easyarmorstands.bukkit.EasyArmorStands;
import me.m56738.easyarmorstands.bukkit.addon.Addon;
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
