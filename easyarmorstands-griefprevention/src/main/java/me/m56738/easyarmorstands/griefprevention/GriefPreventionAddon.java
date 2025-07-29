package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class GriefPreventionAddon implements Addon {
    private GriefPreventionPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "GriefPrevention";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefPreventionPrivilegeChecker();
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
