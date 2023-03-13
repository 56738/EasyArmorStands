package me.m56738.easyarmorstands.addon.worldguard.v6;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.Bukkit;

public class WorldGuardAddon implements Addon {
    @Override
    public boolean isSupported() {
        if (Bukkit.getPluginManager().getPlugin("WorldGuard") == null) {
            return false;
        }
        try {
            Class.forName("com.sk89q.worldguard.bukkit.WGBukkit");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "WorldGuard v6";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        plugin.getServer().getPluginManager().registerEvents(new WorldGuardListener(), plugin);
    }
}
