package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class HeadDatabaseAddonFactory implements AddonFactory<HeadDatabaseAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.headDatabase.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("HeadDatabase") != null;
    }

    @Override
    public HeadDatabaseAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new HeadDatabaseAddon(plugin, eas);
    }
}
