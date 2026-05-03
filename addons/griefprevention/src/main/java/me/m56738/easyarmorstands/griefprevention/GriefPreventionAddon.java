package me.m56738.easyarmorstands.griefprevention;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;

public class GriefPreventionAddon implements Addon {
    private RegionPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "GriefPrevention";
    }

    @Override
    public void enable() {
        privilegeChecker = new GriefPreventionPrivilegeChecker();
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
