package me.m56738.easyarmorstands.residence;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import me.m56738.easyarmorstands.region.RegionPrivilegeChecker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ResidencePrivilegeChecker implements RegionPrivilegeChecker {
    private final Residence residencePlugin;

    public ResidencePrivilegeChecker(Residence residencePlugin) {
        this.residencePlugin = residencePlugin;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        try {
            FlagPermissions perms = residencePlugin.getPermsByLoc(location);
            return perms.playerHas(player, Flags.build, true);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
