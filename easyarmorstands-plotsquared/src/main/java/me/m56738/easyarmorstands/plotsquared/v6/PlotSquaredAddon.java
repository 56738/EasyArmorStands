package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.EasyArmorStands;

public class PlotSquaredAddon {
    public PlotSquaredAddon(EasyArmorStands plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlotSquaredListener(new PlotAPI()), plugin);
    }
}
