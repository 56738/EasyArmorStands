package me.m56738.easyarmorstands.bentobox;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;

public class BentoBoxAddon implements Addon {
    private BentoBoxPrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "BentoBox";
    }

    @Override
    public void enable() {
        privilegeChecker = new BentoBoxPrivilegeChecker();
        EasyArmorStands.get().regionPrivilegeManager().registerPrivilegeChecker(EasyArmorStandsPlugin.getInstance(), privilegeChecker);
    }

    @Override
    public void disable() {
        if (privilegeChecker != null) {
            EasyArmorStands.get().regionPrivilegeManager().unregisterPrivilegeChecker(privilegeChecker);
            privilegeChecker = null;
        }
    }

    @Override
    public void reload() {
    }
}
