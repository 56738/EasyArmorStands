package me.m56738.easyarmorstands.fancyholograms;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class FancyHologramsAddonFactory implements AddonFactory<FancyHologramsAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.fancyHolograms.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("FancyHolograms") != null;
    }

    @Override
    public FancyHologramsAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new FancyHologramsAddon(plugin, eas);
    }
}
