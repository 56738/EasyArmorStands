package me.m56738.easyarmorstands.region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface RegionPrivilegeChecker {
    boolean isAllowed(Player player, Location location);
}
