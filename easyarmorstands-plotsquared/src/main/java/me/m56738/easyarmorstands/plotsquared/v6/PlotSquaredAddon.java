package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class PlotSquaredAddon implements Addon {
    private PlotSquaredPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "PlotSquared";
    }

    @Override
    public void enable() {
        privilegeChecker = new PlotSquaredPrivilegeChecker(new PlotAPI());
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
