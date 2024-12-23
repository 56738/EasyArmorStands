package me.m56738.easyarmorstands.griefdefender;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class GriefDefenderAddonFactory implements AddonFactory<GriefDefenderAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.griefDefender.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefDefender") != null;
    }

    @Override
    public GriefDefenderAddon create() {
        return new GriefDefenderAddon();
    }
}
