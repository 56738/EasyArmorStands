package gg.bundlegroup.easyarmorstands.bukkit.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player attempts to start editing an armor stand.
 */
public class ArmorStandPreSpawnEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final Location location;
    private boolean cancelled;

    public ArmorStandPreSpawnEvent(@NotNull Player player, @NotNull Location location) {
        super(player);
        this.location = location;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public @NotNull Location getLocation() {
        return location;
    }
}
