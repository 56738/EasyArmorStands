package me.m56738.easyarmorstands.plotsquared.v6;

import com.plotsquared.core.PlotAPI;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeChecker;
import org.bukkit.plugin.Plugin;

public class PlotSquaredAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;

    private RegionPrivilegeChecker privilegeChecker;

    public PlotSquaredAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "PlotSquared";
    }

    @Override
    public void enable() {
        privilegeChecker = new PlotSquaredPrivilegeChecker(new PlotAPI());
        eas.regionPrivilegeManager().registerPrivilegeChecker(plugin, privilegeChecker);
    }

    @Override
    public void disable() {
        eas.regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
    }

    @Override
    public void reload() {
    }
}
