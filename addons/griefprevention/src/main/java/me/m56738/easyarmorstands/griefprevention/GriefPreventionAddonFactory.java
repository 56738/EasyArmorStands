package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import org.bukkit.Bukkit;

public class GriefPreventionAddonFactory implements AddonFactory<GriefPreventionAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.griefPrevention.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
    }

    @Override
    public GriefPreventionAddon create() {
        return new GriefPreventionAddon();
    }
}
