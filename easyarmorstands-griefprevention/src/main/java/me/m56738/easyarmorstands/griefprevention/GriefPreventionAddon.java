package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class GriefPreventionAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private GriefPreventionPrivilegeChecker privilegeChecker;

    public GriefPreventionAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "GriefPrevention";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefPreventionPrivilegeChecker();
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
