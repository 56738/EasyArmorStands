package me.m56738.easyarmorstands.lands;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.common.util.ReflectionUtil;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class LandsAddonFactory implements AddonFactory<LandsAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Lands") != null
                && ReflectionUtil.hasClass("me.angeschossen.lands.api.LandsIntegration");
    }

    @Override
    public LandsAddon create(EasyArmorStandsPlugin plugin) {
        return new LandsAddon(plugin);
    }
}
