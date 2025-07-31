package me.m56738.easyarmorstands.fancyholograms;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class FancyHologramsAddonFactory implements AddonFactory<FancyHologramsAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FancyHolograms") != null;
    }

    @Override
    public FancyHologramsAddon create(EasyArmorStandsPlugin plugin) {
        return new FancyHologramsAddon(plugin);
    }
}
