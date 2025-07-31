package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.GriefDefender;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class GriefDefenderAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private GriefDefenderPrivilegeChecker privilegeChecker;

    public GriefDefenderAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "GriefDefender";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefDefenderPrivilegeChecker(GriefDefender.getCore());
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
