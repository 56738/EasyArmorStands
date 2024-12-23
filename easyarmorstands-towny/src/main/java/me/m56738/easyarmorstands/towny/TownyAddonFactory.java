package me.m56738.easyarmorstands.towny;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class TownyAddonFactory implements AddonFactory<TownyAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.towny.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    @Override
    public TownyAddon create() {
        return new TownyAddon();
    }
}
