package me.m56738.easyarmorstands.region;

import me.m56738.easyarmorstands.api.region.RegionPrivilegeChecker;
import me.m56738.easyarmorstands.api.region.RegionPrivilegeManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RegionListenerManager implements RegionPrivilegeManager {
    private final Map<RegionPrivilegeChecker, RegionListener> regionListeners = new HashMap<>();

    @Override
    public void registerPrivilegeChecker(@NotNull Plugin plugin, @NotNull RegionPrivilegeChecker privilegeChecker) {
        RegionListener listener = new RegionListener(privilegeChecker);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        RegionListener old = regionListeners.put(privilegeChecker, listener);
        if (old != null) {
            HandlerList.unregisterAll(old);
        }
    }

    @Override
    public void unregisterPrivilegeChecker(@NotNull RegionPrivilegeChecker privilegeChecker) {
        RegionListener listener = regionListeners.remove(privilegeChecker);
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
    }

    public void unregisterAll() {
        for (RegionListener listener : regionListeners.values()) {
            HandlerList.unregisterAll(listener);
        }
        regionListeners.clear();
    }
}
