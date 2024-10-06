package me.m56738.easyarmorstands.region;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditElementEvent;
import me.m56738.easyarmorstands.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public class RegionListener implements Listener {
    private final Map<Player, Boolean> bypassCache = new WeakHashMap<>();
    private final String bypassPermission;
    private final RegionPrivilegeChecker privilegeChecker;
    private final Component createError;
    private final Component selectError;
    private final Component destroyError;

    public RegionListener(String bypassPermission, RegionPrivilegeChecker privilegeChecker, Component createError, Component selectError, Component destroyError) {
        this.bypassPermission = bypassPermission;
        this.privilegeChecker = privilegeChecker;
        this.createError = createError;
        this.selectError = selectError;
        this.destroyError = destroyError;
    }

    private static Audience audience(Player player) {
        return EasyArmorStandsPlugin.getInstance().getAdventure().player(player);
    }

    private boolean isAllowed(Player player, Location location) {
        return privilegeChecker.isAllowed(player, location);
    }

    @EventHandler
    public void onInitialize(SessionStartEvent event) {
        bypassCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDiscover(PlayerDiscoverElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEdit(PlayerEditElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(selectError);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSpawn(PlayerCreateElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (event.getPlayer().hasPermission(bypassPermission)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(createError);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEdit(PlayerEditPropertyEvent<?> event) {
        Element element = event.getElement();
        if (!(element instanceof EntityElement<?>)) {
            return;
        }
        Entity entity = ((EntityElement<?>) element).getEntity();
        if (isAllowed(event.getPlayer(), entity.getLocation())) {
            if (event.getProperty().getType() != EntityPropertyTypes.LOCATION) {
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
    public void onDestroy(PlayerDestroyElementEvent event) {
        if (isAllowed(event.getPlayer(), event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue())) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::canBypass)) {
            return;
        }
        event.setCancelled(true);
        audience(event.getPlayer()).sendMessage(destroyError);
    }

    private boolean canBypass(Player player) {
        return player.hasPermission(bypassPermission);
    }
}
