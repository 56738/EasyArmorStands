package me.m56738.easyarmorstands.towny;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.paper.addon.Addon;
import org.bukkit.event.HandlerList;

public class TownyAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private TownyPrivilegeChecker privilegeChecker;

    public TownyAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Towny";
    }

    @Override
    public void enable() {
        privilegeChecker = new TownyPrivilegeChecker();
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
