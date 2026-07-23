package me.m56738.easyarmorstands.griefdefender;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class GriefDefenderAddonFactory implements AddonFactory<GriefDefenderAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.griefDefender.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefDefender") != null;
    }

    @Override
    public GriefDefenderAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new GriefDefenderAddon(plugin, eas);
    }
}
