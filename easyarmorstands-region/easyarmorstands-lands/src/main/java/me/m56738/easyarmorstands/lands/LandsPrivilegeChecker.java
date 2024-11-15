package me.m56738.easyarmorstands.lands;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.RoleFlag;
import me.angeschossen.lands.api.land.Area;
import me.m56738.easyarmorstands.region.RegionPrivilegeChecker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LandsPrivilegeChecker implements RegionPrivilegeChecker {
    private final LandsIntegration integration;
    private final RoleFlag flag;

    public LandsPrivilegeChecker(LandsIntegration integration, RoleFlag flag) {
        this.integration = integration;
        this.flag = flag;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        if (!location.getChunk().isLoaded()) {
            return false;
        }
        Area area = integration.getArea(location);
        if (area == null) {
            return true;
        }
        return area.hasRoleFlag(player.getUniqueId(), flag);
    }
}
