package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.region.RegionListener;

public class ResidenceAddon {
    public ResidenceAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RegionListener(
                Permissions.RESIDENCE_BYPASS,
                new ResidencePrivilegeChecker(Residence.getInstance()),
                Message.error("easyarmorstands.error.residence.deny-create"),
                Message.error("easyarmorstands.error.residence.deny-destroy")
        ), plugin);
    }
}
