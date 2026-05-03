package me.m56738.easyarmorstands.fancyholograms;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class FancyHologramsAddonFactory implements AddonFactory<FancyHologramsAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.fancyHolograms.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FancyHolograms") != null;
    }

    @Override
    public FancyHologramsAddon create() {
        return new FancyHologramsAddon();
    }
}
