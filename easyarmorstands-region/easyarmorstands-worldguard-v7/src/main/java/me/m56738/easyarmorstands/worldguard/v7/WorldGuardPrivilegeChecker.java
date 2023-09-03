package me.m56738.easyarmorstands.worldguard.v7;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import me.m56738.easyarmorstands.region.RegionPrivilegeChecker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardPrivilegeChecker implements RegionPrivilegeChecker {
    private final SessionManager sessionManager;
    private final RegionContainer regionContainer;

    public WorldGuardPrivilegeChecker() {
        WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
        this.sessionManager = platform.getSessionManager();
        this.regionContainer = platform.getRegionContainer();
    }

    @Override
    public boolean isAllowed(Player player, Location location) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        if (sessionManager.hasBypass(localPlayer, BukkitAdapter.adapt(location.getWorld()))) {
            return true;
        }
        RegionQuery query = regionContainer.createQuery();
        return query.testState(BukkitAdapter.adapt(location), localPlayer, Flags.BUILD);
    }
}
