package me.m56738.easyarmorstands.worldguard.v6;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.region.RegionListener;

public class WorldGuardAddon {
    public WorldGuardAddon() {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RegionListener(
                "easyarmorstands.worldguard.bypass",
                new WorldGuardPrivilegeChecker(),
                Message.error("easyarmorstands.error.worldguard.deny-create"),
                Message.error("easyarmorstands.error.worldguard.deny-destroy"),
                Message.error("easyarmorstands.error.worldguard.deny-select")
        ), plugin);
    }
}
