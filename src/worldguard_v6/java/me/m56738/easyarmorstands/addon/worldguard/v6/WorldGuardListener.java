package me.m56738.easyarmorstands.addon.worldguard.v6;

import com.sk89q.worldguard.bukkit.WGBukkit;
import me.m56738.easyarmorstands.event.ArmorStandPreSpawnEvent;
import me.m56738.easyarmorstands.event.SessionMoveEvent;
import me.m56738.easyarmorstands.event.SessionStartEvent;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.joml.Vector3dc;

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
    public void onStartSession(SessionStartEvent event) {
        ArmorStand armorStand = event.getArmorStand();
        if (isAllowed(event.getPlayer(), armorStand.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(ArmorStandPreSpawnEvent event) {
        if (isAllowed(event.getPlayer(), event.getLocation())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMoveSession(SessionMoveEvent event) {
        Vector3dc pos = event.getPosition();
        Location location = new Location(event.getWorld(), pos.x(), pos.y(), pos.z());
        if (isAllowed(event.getPlayer(), location)) {
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
