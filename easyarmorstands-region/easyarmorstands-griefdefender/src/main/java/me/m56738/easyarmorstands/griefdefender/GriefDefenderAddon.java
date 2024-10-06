package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.GriefDefender;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.region.RegionListener;

public class GriefDefenderAddon {
    public GriefDefenderAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RegionListener(
                Permissions.GRIEFDEFENDER_BYPASS,
                new GriefDefenderPrivilegeChecker(GriefDefender.getCore()),
                Message.error("easyarmorstands.error.griefdefender.deny-create"),
                Message.error("easyarmorstands.error.griefdefender.deny-select"),
                Message.error("easyarmorstands.error.griefdefender.deny-destroy")
        ), plugin);
    }
}
