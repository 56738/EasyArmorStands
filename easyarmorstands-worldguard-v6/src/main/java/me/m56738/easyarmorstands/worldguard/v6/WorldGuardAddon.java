package me.m56738.easyarmorstands.worldguard.v6;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;

public class WorldGuardAddon implements Addon {
    private RegionPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "WorldGuard";
    }

    @Override
    public void enable() {
        privilegeChecker = new WorldGuardPrivilegeChecker();
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
