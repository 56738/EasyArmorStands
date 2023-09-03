package me.m56738.easyarmorstands.worldguard.v6;

import com.sk89q.worldguard.bukkit.WGBukkit;
import me.m56738.easyarmorstands.region.RegionPrivilegeChecker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardPrivilegeChecker implements RegionPrivilegeChecker {
    @Override
    public boolean isAllowed(Player player, Location location) {
        return WGBukkit.getPlugin().canBuild(player, location);
    }
}
