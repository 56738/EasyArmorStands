package me.m56738.easyarmorstands.towny;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPaperImpl;
import me.m56738.easyarmorstands.paper.addon.Addon;
import me.m56738.easyarmorstands.paper.api.region.RegionPrivilegeChecker;
import org.bukkit.plugin.Plugin;

public class TownyAddon implements Addon {
    private final Plugin plugin;
    private final EasyArmorStandsPaperImpl eas;
    private RegionPrivilegeChecker privilegeChecker;

    public TownyAddon(Plugin plugin, EasyArmorStandsPaperImpl eas) {
        this.plugin = plugin;
        this.eas = eas;
    }

    @Override
    public String name() {
        return "Towny";
    }

    @Override
    public void enable() {
        privilegeChecker = new TownyPrivilegeChecker();
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
