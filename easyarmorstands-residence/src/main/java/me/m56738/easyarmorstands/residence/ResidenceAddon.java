package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.addon.Addon;
import me.m56738.easyarmorstands.api.EasyArmorStands;

public class ResidenceAddon implements Addon {
    private ResidencePrivilegeChecker privilegeChecker;

    @Override
    public String name() {
        return "Residence";
    }

    @Override
    public void enable() {
        privilegeChecker = new ResidencePrivilegeChecker(Residence.getInstance());
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
