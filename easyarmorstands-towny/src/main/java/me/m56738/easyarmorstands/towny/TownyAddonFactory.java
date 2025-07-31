package me.m56738.easyarmorstands.towny;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class TownyAddonFactory implements AddonFactory<TownyAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Towny") != null;
    }

    @Override
    public TownyAddon create(EasyArmorStandsPlugin plugin) {
        return new TownyAddon(plugin);
    }
}
