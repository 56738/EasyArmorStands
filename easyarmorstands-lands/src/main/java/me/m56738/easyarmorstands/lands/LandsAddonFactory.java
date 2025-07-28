package me.m56738.easyarmorstands.lands;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import me.m56738.easyarmorstands.common.util.ReflectionUtil;
import org.bukkit.Bukkit;

public class LandsAddonFactory implements AddonFactory<LandsAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.lands.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Lands") != null
                && ReflectionUtil.hasClass("me.angeschossen.lands.api.LandsIntegration");
    }

    @Override
    public LandsAddon create() {
        return new LandsAddon();
    }
}
