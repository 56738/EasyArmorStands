package me.m56738.easyarmorstands.worldguard.v7;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.region.RegionListener;

public class WorldGuardAddon {
    public WorldGuardAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RegionListener(
                Permissions.WORLDGUARD_BYPASS,
                new WorldGuardPrivilegeChecker(),
                Message.error("easyarmorstands.error.worldguard.deny-create"),
                Message.error("easyarmorstands.error.worldguard.deny-destroy"),
                Message.error("easyarmorstands.error.worldguard.deny-select")
        ), plugin);
    }
}
