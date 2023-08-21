package me.m56738.easyarmorstands.addon.worldguard.v7;

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
            Class.forName("com.sk89q.worldguard.protection.regions.RegionContainer");
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "WorldGuard v7";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        plugin.getServer().getPluginManager().registerEvents(new WorldGuardListener(), plugin);
    }

    @Override
    public void reload(EasyArmorStands plugin) {
    }
}
