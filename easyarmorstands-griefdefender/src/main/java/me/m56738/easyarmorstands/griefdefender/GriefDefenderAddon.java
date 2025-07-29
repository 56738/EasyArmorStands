package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.GriefDefender;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class GriefDefenderAddon implements Addon {
    private GriefDefenderPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "GriefDefender";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefDefenderPrivilegeChecker(GriefDefender.getCore());
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
