package me.m56738.easyarmorstands.api.region;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface RegionPrivilegeManager {
    void registerPrivilegeChecker(@NotNull Plugin plugin, @NotNull RegionPrivilegeChecker privilegeChecker);

    void unregisterPrivilegeChecker(@NotNull RegionPrivilegeChecker privilegeChecker);
}
