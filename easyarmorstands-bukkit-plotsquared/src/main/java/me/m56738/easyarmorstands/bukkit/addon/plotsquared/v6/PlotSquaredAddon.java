package me.m56738.easyarmorstands.bukkit.addon.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.bukkit.EasyArmorStands;
import me.m56738.easyarmorstands.bukkit.addon.Addon;
import org.bukkit.Bukkit;

public class PlotSquaredAddon implements Addon {
    @Override
    public boolean isSupported() {
        return Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
    }

    @Override
    public String getName() {
        return "PlotSquared";
    }

    @Override
    public void enable(EasyArmorStands plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlotSquaredListener(new PlotAPI()), plugin);
    }
}
