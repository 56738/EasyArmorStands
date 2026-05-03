package me.m56738.easyarmorstands.plotsquared.v6;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class PlotSquaredAddonFactory implements AddonFactory<PlotSquaredAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.plotSquared.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
    }

    @Override
    public PlotSquaredAddon create() {
        return new PlotSquaredAddon();
    }
}
