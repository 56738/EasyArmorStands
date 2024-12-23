package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.GriefDefender;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;

public class GriefDefenderAddon implements Addon {
    private RegionPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "GriefDefender";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefDefenderPrivilegeChecker(GriefDefender.getCore());
        EasyArmorStands.get().regionPrivilegeManager().registerPrivilegeChecker(EasyArmorStandsPlugin.getInstance(), privilegeChecker);
    }

    @Override
    public void disable() {
        EasyArmorStands.get().regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
    }

    @Override
    public void reload() {
    }
}
