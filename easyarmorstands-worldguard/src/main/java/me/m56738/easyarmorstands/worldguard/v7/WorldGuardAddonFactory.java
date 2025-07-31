package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.paper.addon.AddonFactory;
import me.m56738.easyarmorstands.common.util.ReflectionUtil;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;
import org.bukkit.Bukkit;

public class WorldGuardAddonFactory implements AddonFactory<WorldGuardAddon> {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return Bukkit.getPluginManager().getPlugin("WorldGuard") != null &&
                ReflectionUtil.hasClass("com.sk89q.worldguard.protection.regions.RegionContainer");
    }

    @Override
    public WorldGuardAddon create(EasyArmorStandsPlugin plugin) {
        return new WorldGuardAddon(plugin);
    }
}
