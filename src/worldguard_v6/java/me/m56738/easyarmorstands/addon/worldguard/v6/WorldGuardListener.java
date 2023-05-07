package me.m56738.easyarmorstands.addon.worldguard.v6;

import com.sk89q.worldguard.bukkit.WGBukkit;
import me.m56738.easyarmorstands.event.SessionEditEntityEvent;
import me.m56738.easyarmorstands.event.SessionPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionSelectEntityEvent;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public class WorldGuardListener implements Listener {
    private final Map<Session, Boolean> bypassCache = new WeakHashMap<>();
    private final String bypassPermission = "easyarmorstands.worldguard.bypass";

    public WorldGuardListener() {
    }

    private boolean isAllowed(Player player, Location location) {
        return WGBukkit.getPlugin().canBuild(player, location);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onStartSession(SessionSelectEntityEvent event) {
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
    public void onSpawn(SessionPreSpawnEvent event) {
        if (isAllowed(event.getPlayer(), event.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoveSession(SessionEditEntityEvent<?, ?> event) {
        if (!(event.getProperty() instanceof EntityLocationProperty)) {
            return;
        }
        if (isAllowed(event.getPlayer(), (Location) event.getNewValue())) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getSession(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
    }

    private boolean canBypass(Session session) {
        return session.getPlayer().hasPermission(bypassPermission);
    }
}
