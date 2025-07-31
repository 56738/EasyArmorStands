package me.m56738.easyarmorstands.plotsquared.v6;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class PlotSquaredAddonFactory implements AddonFactory<PlotSquaredAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
    }

    @Override
    public PlotSquaredAddon create(EasyArmorStandsPlugin plugin) {
        return new PlotSquaredAddon(plugin);
    }
}
