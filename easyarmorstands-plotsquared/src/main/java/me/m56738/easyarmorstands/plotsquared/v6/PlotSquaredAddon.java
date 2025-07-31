package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.event.HandlerList;

public class PlotSquaredAddon implements Addon {
    private final EasyArmorStandsPlugin plugin;
    private PlotSquaredPrivilegeChecker privilegeChecker;

    public PlotSquaredAddon(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "PlotSquared";
    }

    @Override
    public void enable() {
        privilegeChecker = new PlotSquaredPrivilegeChecker(new PlotAPI());
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
