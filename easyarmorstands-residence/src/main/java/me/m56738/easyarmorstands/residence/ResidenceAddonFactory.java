package me.m56738.easyarmorstands.residence;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class ResidenceAddonFactory implements AddonFactory<ResidenceAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Residence") != null;
    }

    @Override
    public ResidenceAddon create(EasyArmorStandsPlugin plugin) {
        return new ResidenceAddon(plugin);
    }
}
