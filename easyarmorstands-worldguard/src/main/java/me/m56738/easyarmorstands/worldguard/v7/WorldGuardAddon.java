package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class WorldGuardAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private WorldGuardPrivilegeChecker privilegeChecker;

    public WorldGuardAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "WorldGuard";
    }

    @Override
    public void enable() {
        privilegeChecker = new WorldGuardPrivilegeChecker();
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
