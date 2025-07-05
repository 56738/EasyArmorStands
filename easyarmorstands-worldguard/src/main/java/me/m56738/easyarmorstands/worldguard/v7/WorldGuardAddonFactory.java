package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.AddonFactory;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import org.bukkit.Bukkit;

public class WorldGuardAddonFactory implements AddonFactory<WorldGuardAddon> {
    @Override
    public boolean isEnabled() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().integration.worldGuard.enabled;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("WorldGuard") != null &&
                ReflectionUtil.hasClass("com.sk89q.worldguard.protection.regions.RegionContainer");
    }

    @Override
    public WorldGuardAddon create() {
        return new WorldGuardAddon();
    }
}
