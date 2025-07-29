package me.m56738.easyarmorstands.paper.api.region;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerCreateElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDestroyElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerDiscoverElementEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerEditPropertyEvent;
import me.m56738.easyarmorstands.paper.api.event.player.PlayerSelectElementEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public abstract class RegionListener implements Listener {
    private final Map<Player, Boolean> bypassCache = new WeakHashMap<>();

    public abstract boolean isAllowed(Player player, Location location, boolean silent);

    public abstract boolean canBypass(Player player);

    public abstract void sendCreateError(Player player, PropertyContainer properties);

    public abstract void sendDestroyError(Player player, Element element);

    public abstract void sendEditError(Player player, Element element);

    @EventHandler
    private void onInitialize(SessionStartEvent event) {
        bypassCache.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onDiscover(PlayerDiscoverElementEvent event) {
        if (hasBypass(event.getPlayer())) {
            return;
        }
        if (isAllowed(event.getPlayer(), PaperLocationAdapter.toNative(event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue()), true)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onEdit(PlayerSelectElementEvent event) {
        if (hasBypass(event.getPlayer())) {
            return;
        }
        if (isAllowed(event.getPlayer(), PaperLocationAdapter.toNative(event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue()), false)) {
            return;
        }
        event.setCancelled(true);
        sendEditError(event.getPlayer(), event.getElement());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onSpawn(PlayerCreateElementEvent event) {
        if (hasBypass(event.getPlayer())) {
            return;
        }
        if (isAllowed(event.getPlayer(), PaperLocationAdapter.toNative(event.getProperties().get(EntityPropertyTypes.LOCATION).getValue()), false)) {
            return;
        }
        event.setCancelled(true);
        sendCreateError(event.getPlayer(), event.getProperties());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onEdit(PlayerEditPropertyEvent<?> event) {
        Element element = event.getElement();
        if (!(element instanceof EntityElement)) {
            return;
        }
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::hasBypass)) {
            return;
        }
        if (isAllowed(event.getPlayer(), PaperLocationAdapter.toNative(element.getProperties().get(EntityPropertyTypes.LOCATION).getValue()), true)) {
            if (event.getProperty().getType() != EntityPropertyTypes.LOCATION) {
                return;
            }
            if (isAllowed(event.getPlayer(), (Location) event.getNewValue(), true)) {
                return;
            }
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onDestroy(PlayerDestroyElementEvent event) {
        if (bypassCache.computeIfAbsent(event.getPlayer(), this::hasBypass)) {
            return;
        }
        if (isAllowed(event.getPlayer(), PaperLocationAdapter.toNative(event.getElement().getProperties().get(EntityPropertyTypes.LOCATION).getValue()), false)) {
            return;
        }
        event.setCancelled(true);
        sendDestroyError(event.getPlayer(), event.getElement());
    }

    private boolean hasBypass(Player player) {
        boolean bypass = canBypass(player);
        bypassCache.put(player, bypass);
        return bypass;
    }
}
