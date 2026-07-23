package me.m56738.easyarmorstands.towny;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class TownyAddonFactory implements AddonFactory<TownyAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.towny.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    @Override
    public TownyAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new TownyAddon(plugin, eas);
    }
}
