package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class GriefPreventionAddonFactory implements AddonFactory<GriefPreventionAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("GriefPrevention") != null;
    }

    @Override
    public GriefPreventionAddon create(EasyArmorStandsPlugin plugin) {
        return new GriefPreventionAddon(plugin);
    }
}
