package me.m56738.easyarmorstands.residence;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ResidenceAddonFactory implements AddonFactory<ResidenceAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.residence.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Residence") != null;
    }

    @Override
    public ResidenceAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new ResidenceAddon(plugin, eas);
    }
}
