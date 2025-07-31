package me.m56738.easyarmorstands.griefdefender;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class GriefDefenderAddonFactory implements AddonFactory<GriefDefenderAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefDefender") != null;
    }

    @Override
    public GriefDefenderAddon create(EasyArmorStandsPlugin plugin) {
        return new GriefDefenderAddon(plugin);
    }
}
