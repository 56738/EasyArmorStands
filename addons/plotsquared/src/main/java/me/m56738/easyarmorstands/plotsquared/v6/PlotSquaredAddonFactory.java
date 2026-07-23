package me.m56738.easyarmorstands.plotsquared.v6;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PlotSquaredAddonFactory implements AddonFactory<PlotSquaredAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.plotSquared.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
    }

    @Override
    public PlotSquaredAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new PlotSquaredAddon(plugin, eas);
    }
}
