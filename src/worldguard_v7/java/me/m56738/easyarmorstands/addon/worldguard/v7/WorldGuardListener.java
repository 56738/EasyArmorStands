package me.m56738.easyarmorstands.addon.worldguard.v7;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import me.m56738.easyarmorstands.event.PlayerDestroyEntityEvent;
import me.m56738.easyarmorstands.event.PlayerEditEntityPropertyEvent;
import me.m56738.easyarmorstands.event.PlayerPreSpawnEntityEvent;
import me.m56738.easyarmorstands.event.SessionInitializeEvent;
import me.m56738.easyarmorstands.event.SessionSelectEntityEvent;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public class WorldGuardListener implements Listener {
    private final Map<Player, Boolean> bypassCache = new WeakHashMap<>();
    private final String bypassPermission = "easyarmorstands.worldguard.bypass";
    private final SessionManager sessionManager;
    private final RegionContainer regionContainer;

    public WorldGuardListener() {
        WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
        this.sessionManager = platform.getSessionManager();
        this.regionContainer = platform.getRegionContainer();
    }

    private boolean isAllowed(Player player, Location location) {
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        if (sessionManager.hasBypass(localPlayer, BukkitAdapter.adapt(location.getWorld()))) {
            return true;
        }
        RegionQuery query = regionContainer.createQuery();
        return query.testState(BukkitAdapter.adapt(location), localPlayer, Flags.BUILD);
    }

    @EventHandler
    public void onInitialize(SessionInitializeEvent event) {
        bypassCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSelect(SessionSelectEntityEvent event) {
        Entity entity = event.getEntity();
        if (isAllowed(event.getPlayer(), entity.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(PlayerPreSpawnEntityEvent event) {
        if (isAllowed(event.getPlayer(), event.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEdit(PlayerEditEntityPropertyEvent<?, ?> event) {
        if (isAllowed(event.getPlayer(), event.getEntity().getLocation())) {
            if (!(event.getProperty() instanceof EntityLocationProperty)) {
                return;
            }
            if (isAllowed(event.getPlayer(), (org.bukkit.Location) event.getNewValue())) {
                return;
            }
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDestroy(PlayerDestroyEntityEvent event) {
        if (isAllowed(event.getPlayer(), event.getEntity().getLocation())) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    private boolean canBypass(Player player) {
        return player.hasPermission(bypassPermission);
    }
}
