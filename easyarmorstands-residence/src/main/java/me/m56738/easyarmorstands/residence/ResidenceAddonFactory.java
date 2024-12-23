package me.m56738.easyarmorstands.residence;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class ResidenceAddonFactory implements AddonFactory<ResidenceAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.residence.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Residence") != null;
    }

    @Override
    public ResidenceAddon create() {
        return new ResidenceAddon();
    }
}
