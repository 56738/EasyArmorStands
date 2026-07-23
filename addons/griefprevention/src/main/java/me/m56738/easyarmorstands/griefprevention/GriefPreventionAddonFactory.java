package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class GriefPreventionAddonFactory implements AddonFactory<GriefPreventionAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.griefPrevention.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
    }

    @Override
    public GriefPreventionAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new GriefPreventionAddon(plugin, eas);
    }
}
