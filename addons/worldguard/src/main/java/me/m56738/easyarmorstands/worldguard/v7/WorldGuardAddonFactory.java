package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldGuardAddonFactory implements AddonFactory<WorldGuardAddon> {
    @Override
    public boolean isEnabled(EasConfig config) {
        return config.integration.worldGuard.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("WorldGuard") != null &&
                ReflectionUtil.hasClass("com.sk89q.worldguard.protection.regions.RegionContainer");
    }

    @Override
    public WorldGuardAddon create(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        return new WorldGuardAddon(plugin, eas);
    }
}
