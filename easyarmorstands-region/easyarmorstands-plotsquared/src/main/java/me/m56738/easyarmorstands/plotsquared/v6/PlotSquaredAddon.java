package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.region.RegionListener;

public class PlotSquaredAddon {
    public PlotSquaredAddon() {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        plugin.getServer().getPluginManager().registerEvents(new RegionListener(
                Permissions.PLOTSQUARED_BYPASS,
                new PlotSquaredPrivilegeChecker(new PlotAPI()),
                Message.error("easyarmorstands.error.plotsquared.deny-create"),
                Message.error("easyarmorstands.error.plotsquared.deny-select"),
                Message.error("easyarmorstands.error.plotsquared.deny-destroy")
        ), plugin);
    }
}
