package me.m56738.easyarmorstands.lands;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LandsAddonFactory implements AddonFactory<LandsAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.lands.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("Lands") != null
                && ReflectionUtil.hasClass("me.angeschossen.lands.api.LandsIntegration");
    }

    @Override
    public LandsAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new LandsAddon(plugin, eas);
    }
}
