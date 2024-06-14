package me.m56738.easyarmorstands.griefdefender;

import com.griefdefender.api.Core;
import com.griefdefender.api.claim.Claim;
import com.griefdefender.api.claim.TrustTypes;
import me.m56738.easyarmorstands.region.RegionPrivilegeChecker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GriefDefenderPrivilegeChecker implements RegionPrivilegeChecker {
    private final Core core;

    public GriefDefenderPrivilegeChecker(Core core) {
        this.core = core;
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        if (!core.isEnabled(location.getWorld().getUID())) {
            return true;
        }
        Claim claim = core.getClaimAt(location);
        if (claim == null || claim.isWilderness()) {
            return true;
        }
        return claim.isUserTrusted(player.getUniqueId(), TrustTypes.BUILDER);
    }
}
