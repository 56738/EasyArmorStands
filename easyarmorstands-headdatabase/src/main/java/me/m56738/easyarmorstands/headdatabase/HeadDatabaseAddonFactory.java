package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class HeadDatabaseAddonFactory implements AddonFactory<HeadDatabaseAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.headDatabase.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HeadDatabase") != null;
    }

    @Override
    public HeadDatabaseAddon create() {
        return new HeadDatabaseAddon();
    }
}
