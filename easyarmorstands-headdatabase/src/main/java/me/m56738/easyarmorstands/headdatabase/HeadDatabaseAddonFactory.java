package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class HeadDatabaseAddonFactory implements AddonFactory<HeadDatabaseAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HeadDatabase") != null;
    }

    @Override
    public HeadDatabaseAddon create(EasyArmorStandsPlugin plugin) {
        return new HeadDatabaseAddon(plugin);
    }
}
