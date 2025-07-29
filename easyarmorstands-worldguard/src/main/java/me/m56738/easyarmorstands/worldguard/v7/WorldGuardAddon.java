package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class WorldGuardAddon implements Addon {
    private WorldGuardPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "WorldGuard";
    }

    @Override
    public void enable() {
        privilegeChecker = new WorldGuardPrivilegeChecker();
        Plugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(privilegeChecker, plugin);
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(privilegeChecker);
    }

    @Override
    public void reload() {
    }
}
