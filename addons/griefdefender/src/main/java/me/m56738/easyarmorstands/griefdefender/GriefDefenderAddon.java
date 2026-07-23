package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.GriefDefender;
import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeChecker;
import org.bukkit.plugin.Plugin;

public class GriefDefenderAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;

    private RegionPrivilegeChecker privilegeChecker;

    public GriefDefenderAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "GriefDefender";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefDefenderPrivilegeChecker(GriefDefender.getCore());
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
